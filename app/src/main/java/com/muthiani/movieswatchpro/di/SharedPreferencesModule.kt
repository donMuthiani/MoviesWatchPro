package com.muthiani.movieswatchpro.di

import android.content.Context
import android.content.SharedPreferences
import com.muthiani.movieswatchpro.shared.MyPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharePreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideMySharedPreferences(sharedPreferences: SharedPreferences): MyPreferences {
        return MyPreferences(sharedPreferences)
    }
}