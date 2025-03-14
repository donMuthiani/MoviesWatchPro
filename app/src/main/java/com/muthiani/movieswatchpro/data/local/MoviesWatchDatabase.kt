package com.muthiani.movieswatchpro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class, RemoteKeysEntity::class, MovieEntityWatchList::class, RemoteKeysWatchList::class], version = 6, exportSchema = true)
@TypeConverters(MoviesConverters::class)
abstract class MoviesWatchDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesWatchDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}
