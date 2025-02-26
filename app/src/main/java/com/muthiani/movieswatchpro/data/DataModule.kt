package com.muthiani.movieswatchpro.data

import android.content.Context
import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MoviesWatchDao
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.data.remote.MoviesWatchApi
import com.muthiani.movieswatchpro.data.remote.PopularMovieRemoteMediator
import com.muthiani.movieswatchpro.data.repository.MovieRepositoryImpl
import com.muthiani.movieswatchpro.data.sharedPrefs.MyPreferences
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideTokenBearerInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYmYxYjI0OWI4MGJhMzBlYjc4ZDMwNmExYzAzMmM2NyIsIm5iZiI6MTczMTQ4NTg3Ni4xNDU5ODU4LCJzdWIiOiI1ODFkYThjMzkyNTE0MTBlZTMwMDc0MmEiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.IUABnE-zmd8PzLCuYl6QrtIww_kRn7qeKZ3utogjWMY",
                    )
                    .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenBearerInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(tokenBearerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesWatchApi(retrofit: Retrofit): MoviesWatchApi {
        return retrofit.create(MoviesWatchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesDatabase(context: Context): MoviesWatchDatabase {
        return Room.databaseBuilder(context, MoviesWatchDatabase::class.java, "movie_db").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesDao(database: MoviesWatchDatabase): MoviesWatchDao {
        return database.moviesDao()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providesPager(
        moviesWatchApi: MoviesWatchApi,
        moviesWatchDatabase: MoviesWatchDatabase,
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator =
                PopularMovieRemoteMediator(
                    api = moviesWatchApi,
                    moviesWatchDatabase = moviesWatchDatabase,
                ),
            pagingSourceFactory = { moviesWatchDatabase.moviesDao().getPopularPagingSource() },
        )
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        moviePager: Pager<Int, MovieEntity>,
        moviesWatchApi: MoviesWatchApi,
        moviesWatchDatabase: MoviesWatchDatabase,
    ): MovieRepository {
        return MovieRepositoryImpl(moviePager, moviesWatchApi, moviesWatchDatabase)
    }

    @Singleton
    @Provides
    fun provideSharePreference(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideMySharedPreferences(sharedPreferences: SharedPreferences): MyPreferences {
        return MyPreferences(sharedPreferences)
    }
}
