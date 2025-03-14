package com.muthiani.movieswatchpro.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesWatchDao {
    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("SELECT * FROM movies")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(users: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getPopularPagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies ORDER BY releaseDate DESC")
    fun getNowShowingPagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE releaseDate >= DATE(CURRENT_DATE, '-30 days')")
    fun getUpcomingPagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM watchlist")
    suspend fun clearAllWatchList()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWatchListMovies(users: List<MovieEntityWatchList>)

    @Query("SELECT * FROM watchlist")
    fun watchLisPagingSource(): PagingSource<Int, MovieEntityWatchList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWatchListMovie(movie: MovieEntityWatchList)

    @Query("DELETE FROM watchlist WHERE id = :id")
    fun deleteWatchListMovie(id: Int)
}
