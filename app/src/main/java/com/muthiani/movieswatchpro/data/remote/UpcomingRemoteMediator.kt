// package com.muthiani.movieswatchpro.data.remote
//
// import androidx.paging.ExperimentalPagingApi
// import androidx.paging.LoadType
// import androidx.paging.PagingState
// import androidx.paging.RemoteMediator
// import androidx.room.withTransaction
// import com.muthiani.movieswatchpro.domain.repository.MovieRepository
// import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
// import com.muthiani.movieswatchpro.data.local.RemoteKeysEntity
// import com.muthiani.movieswatchpro.domain.entity.MovieModel
// import retrofit2.HttpException
// import java.io.IOException
//
// @OptIn(ExperimentalPagingApi::class)
// class UpcomingRemoteMediator(
//    private val query: String = "",
//    val repository: MovieRepository,
//    val moviesWatchDatabase: MoviesWatchDatabase,
// ) : RemoteMediator<Int, MovieModel>() {
//    private val moviesWatchDao = moviesWatchDatabase.moviesDao()
//    private val remoteKeysDao = moviesWatchDatabase.remoteKeysDao()
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, MovieModel>,
//    ): MediatorResult {
//        return try {
//            val loadKey =
//                when (loadType) {
//                    LoadType.REFRESH -> null
//                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                    LoadType.APPEND -> {
//                        val remoteKey =
//                            moviesWatchDatabase.withTransaction {
//                                remoteKeysDao.remoteKeyByQuery(query)
//                            }
//                        if (remoteKey.nextKey == null) {
//                            return MediatorResult.Success(
//                                endOfPaginationReached = true,
//                            )
//                        }
//                        remoteKey.nextKey
//                    }
//                }
//
//            val response = repository.getUpcomingMovies(page = loadKey ?: 1)
//            moviesWatchDatabase.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    moviesWatchDao.clearAll()
//                    remoteKeysDao.deleteByQuery(query)
//                }
//
//                val nextKey = (response.page) + 1
//
//                remoteKeysDao.insertOrReplace(
//                    RemoteKeysEntity(0, query, nextKey),
//                )
//
//                moviesWatchDao.insertMovies(response.data.orEmpty())
//            }
//            MediatorResult.Success(endOfPaginationReached = (response.page ?: 0) >= (response.totalPages ?: 0))
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
// }
