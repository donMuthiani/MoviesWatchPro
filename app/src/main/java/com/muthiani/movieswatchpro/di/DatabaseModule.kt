package com.muthiani.movieswatchpro.di

import android.content.Context
import androidx.room.Room
import com.muthiani.movieswatchpro.db.MoviesWatchDao
import com.muthiani.movieswatchpro.db.MoviesWatchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(context: Context): MoviesWatchDatabase {
        return Room.databaseBuilder(context, MoviesWatchDatabase::class.java, "app_db").build()
    }

    @Provides
    fun providesDao(database: MoviesWatchDatabase): MoviesWatchDao {
        return database.moviesDao()
    }
}
