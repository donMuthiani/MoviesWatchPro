package com.muthiani.movieswatchpro.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.muthiani.movieswatchpro.data.ApiCallType
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.db.MoviesWatchDatabase
import com.muthiani.movieswatchpro.models.MovieModel
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieListRemoteMediator(
    private val query: String = "",
    val repository: MovieRepository,
    val moviesWatchDatabase: MoviesWatchDatabase,
    val apiCallType: String,
) : RemoteMediator<Int, MovieModel>() {
    private val moviesWatchDao = moviesWatchDatabase.moviesDao()
    private val remoteKeysDao = moviesWatchDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieModel>,
    ): MediatorResult {
        return try {
            val loadKey =
                when (loadType) {
                    LoadType.REFRESH -> null
                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                    LoadType.APPEND -> {
                        val remoteKey =
                            moviesWatchDatabase.withTransaction {
                                remoteKeysDao.remoteKeyByQuery(query)
                            }
                        if (remoteKey.nextKey == null) {
                            return MediatorResult.Success(
                                endOfPaginationReached = true,
                            )
                        }
                        remoteKey.nextKey
                    }
                }

            val response =
                when (apiCallType) {
                    ApiCallType.POPULAR.name -> {
                        repository.getPopularMovies(page = loadKey ?: 1)
                    }
                    ApiCallType.UPCOMING.name -> {
                        repository.getUpcomingMovies(page = loadKey ?: 1)
                    }
                    else -> {
                        repository.getNowShowingMovies(page = loadKey ?: 1)
                    }
                }
            moviesWatchDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesWatchDao.clearAll()
                    remoteKeysDao.deleteByQuery(query)
                }

                val nextKey = response.page + 1

                remoteKeysDao.insertOrReplace(
                    RemoteKeys(label = query, nextKey = nextKey),
                )
                moviesWatchDao.insertMovies(response.data.orEmpty())
            }
            MediatorResult.Success(endOfPaginationReached = response.page >= response.totalPages)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
