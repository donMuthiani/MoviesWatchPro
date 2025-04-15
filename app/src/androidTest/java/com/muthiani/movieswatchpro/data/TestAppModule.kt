package com.muthiani.movieswatchpro.data

import android.content.Context
import android.content.SharedPreferences
import com.muthiani.movieswatchpro.data.config.ApiLoadTypeHolder
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
class TestAppModule {
    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideApiTypeHolder(): ApiLoadTypeHolder {
        return ApiLoadTypeHolder()
    }

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }
}
