package com.muthiani.movieswatchpro.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muthiani.movieswatchpro.data.pagination.RemoteKeys
import com.muthiani.movieswatchpro.models.MovieModel

@Database(entities = [MovieModel::class, RemoteKeys::class], version = 1, exportSchema = true)
@TypeConverters(BelongsToCollectionConverter::class)
abstract class MoviesWatchDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesWatchDao

    abstract fun remoteKeysDao(): RemoteKeyDao
}
