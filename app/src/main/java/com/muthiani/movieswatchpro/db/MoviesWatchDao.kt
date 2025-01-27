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

    @Query("SELECT * FROM movies WHERE title LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(users: List<MovieModel>)
}
