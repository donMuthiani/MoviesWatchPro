// package com.muthiani.movieswatchpro.data.remote
//
// import androidx.paging.ExperimentalPagingApi
// import androidx.paging.LoadType
// import androidx.paging.PagingState
// import androidx.paging.RemoteMediator
// import androidx.room.withTransaction
// import com.muthiani.movieswatchpro.domain.repository.MovieRepository
// import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
// import com.muthiani.movieswatchpro.data.local.RemoteKeysViewMore
// import com.muthiani.movieswatchpro.domain.entity.ApiCallType
// import retrofit2.HttpException
// import timber.log.Timber
// import java.io.IOException
//
// @OptIn(ExperimentalPagingApi::class)
// class MovieListRemoteMediator(
//    private val query: String = "",
//    val repository: MovieRepository,
//    val moviesWatchDatabase: MoviesWatchDatabase,
//    val apiCallType: String,
// ) : RemoteMediator<Int, MovieModelViewMore>() {
//    private val moviesWatchDao = moviesWatchDatabase.moviesDao()
//    private val remoteKeysDao = moviesWatchDatabase.remoteKeysDao()
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, MovieModelViewMore>,
//    ): MediatorResult {
//        return try {
//            Timber.i("LoadType $loadType")
//            val remoteKey =
//                moviesWatchDatabase.withTransaction {
//                    remoteKeysDao.getMostRecentRemoteKey()
//                }
//            Timber.tag("Mediator").i("MovieListRemoteMediator RemoteKey for query '$query': $remoteKey")
//            val loadKey =
//                when (loadType) {
//                    LoadType.REFRESH -> {
//                        null
//                    }
//                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                    LoadType.APPEND -> {
//                        if (remoteKey?.nextKey == null) {
//                            return MediatorResult.Success(
//                                endOfPaginationReached = true,
//                            )
//                        }
//                        remoteKey.nextKey
//                    }
//                }
//
//            val response =
//                when (apiCallType) {
//                    ApiCallType.POPULAR.name -> {
//                        repository.getPopularMovies(page = loadKey ?: 1)
//                    }
//                    ApiCallType.UPCOMING.name -> {
//                        repository.getUpcomingMovies(page = loadKey ?: 1)
//                    }
//                    else -> {
//                        repository.getNowShowingMovies(page = loadKey ?: 1)
//                    }
//                }
//            moviesWatchDatabase.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    moviesWatchDao.viewMoreClearAll()
//                    remoteKeysDao.deleteByQueryViewMore(query)
//                }
//
//                val nextKey = response.page + 1
//
//                remoteKeysDao.insertOrReplaceViewMoere(
//                    RemoteKeysViewMore(label = query, nextKey = nextKey),
//                )
//                Timber.tag("Mediator").i("MovieListRemoteMediator Saving RemoteKey for query '$query': nextKey=$nextKey")
//                Timber.tag("Mediator").i("MovieListRemoteMediator Saved RemoteKey for query '$query': Key=${remoteKeysDao.getMostRecentRemoteKey()}")
//
//                moviesWatchDao.insertMoviesViewMore(response.data.orEmpty().map {
//                    MovieModelViewMore(it)
//                })
//
//                moviesWatchDao.pagingSourceViewMore().invalidate()
//            }
//            MediatorResult.Success(endOfPaginationReached = response.page >= response.totalPages)
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
// }
