package com.muthiani.movieswatchpro.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muthiani.movieswatchpro.models.MovieModel

@Database(entities = [MovieModel::class], version = 1, exportSchema = true)
abstract class MoviesWatchDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesWatchDao
}
