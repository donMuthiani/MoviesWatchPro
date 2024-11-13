package com.muthiani.movieswatchpro.di

import com.muthiani.movieswatchpro.data.FakeWatchListRepository
import com.muthiani.movieswatchpro.data.MoviesWatchApi
import com.muthiani.movieswatchpro.data.impl.FakeWatchListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideFakeWatchListRepository(moviesWatchApi: MoviesWatchApi): FakeWatchListRepository {
        return FakeWatchListRepositoryImpl(moviesWatchApi)
    }
}
