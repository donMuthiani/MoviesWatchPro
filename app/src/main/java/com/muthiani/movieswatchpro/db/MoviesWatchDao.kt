package com.muthiani.movieswatchpro.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muthiani.movieswatchpro.models.MovieModel

@Dao
interface MoviesWatchDao {
    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("SELECT * FROM movies")
    fun pagingSource(): PagingSource<Int, MovieModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(users: List<MovieModel>)

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getPopularPagingSource(): PagingSource<Int, MovieModel>

    @Query("SELECT * FROM movies ORDER BY releaseDate DESC")
    fun getNowShowingPagingSource(): PagingSource<Int, MovieModel>

    @Query("SELECT * FROM movies WHERE releaseDate >= DATE(:today, '-14 days')")
    fun getUpcomingPagingSource(today: String): PagingSource<Int, MovieModel>
}
