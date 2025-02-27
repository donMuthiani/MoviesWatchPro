package com.muthiani.movieswatchpro.data

import android.content.Context
import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.muthiani.movieswatchpro.BuildConfig
import com.muthiani.movieswatchpro.data.config.ApiLoadTypeHolder
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MoviesWatchDao
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.data.remote.MoviesRemoteMediator
import com.muthiani.movieswatchpro.data.remote.MoviesWatchApi
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
import javax.inject.Named
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
                        BuildConfig.API_TOKEN,
//                        ApiConstants.TOKEN
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

    @Provides
    @Singleton
    fun provideApiTypeHolder(): ApiLoadTypeHolder {
        return ApiLoadTypeHolder() // Provide the instance
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("view_more")
    fun providesViewMorePager(
        moviesWatchApi: MoviesWatchApi,
        moviesWatchDatabase: MoviesWatchDatabase,
        apiTypeHolder: ApiLoadTypeHolder,
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator =
                MoviesRemoteMediator(
                    api = moviesWatchApi,
                    moviesWatchDatabase = moviesWatchDatabase,
                    apiType = apiTypeHolder.apiType,
                ),
            pagingSourceFactory = { moviesWatchDatabase.moviesDao().pagingSource() },
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("popular")
    fun providesPopularPager(
        moviesWatchApi: MoviesWatchApi,
        moviesWatchDatabase: MoviesWatchDatabase,
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator =
                MoviesRemoteMediator(
                    api = moviesWatchApi,
                    moviesWatchDatabase = moviesWatchDatabase,
                    apiType = "popular",
                ),
            pagingSourceFactory = { moviesWatchDatabase.moviesDao().getPopularPagingSource() },
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("upcoming")
    fun providesUpcomingPager(
        moviesWatchApi: MoviesWatchApi,
        moviesWatchDatabase: MoviesWatchDatabase,
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator =
                MoviesRemoteMediator(
                    api = moviesWatchApi,
                    moviesWatchDatabase = moviesWatchDatabase,
                    apiType = "upcoming",
                ),
            pagingSourceFactory = { moviesWatchDatabase.moviesDao().getUpcomingPagingSource() },
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("now_showing")
    fun providesNowShowingPager(
        moviesWatchApi: MoviesWatchApi,
        moviesWatchDatabase: MoviesWatchDatabase,
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator =
                MoviesRemoteMediator(
                    api = moviesWatchApi,
                    moviesWatchDatabase = moviesWatchDatabase,
                    apiType = "now_showing",
                ),
            pagingSourceFactory = { moviesWatchDatabase.moviesDao().getNowShowingPagingSource() },
        )
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        @Named("popular") popularPager: Pager<Int, MovieEntity>,
        @Named("upcoming") upcomingPager: Pager<Int, MovieEntity>,
        @Named("now_showing") nowShowingPager: Pager<Int, MovieEntity>,
        @Named("view_more") viewMorePager: Pager<Int, MovieEntity>,
        moviesWatchApi: MoviesWatchApi,
    ): MovieRepository {
        return MovieRepositoryImpl(popularPager, upcomingPager, nowShowingPager, viewMorePager, moviesWatchApi)
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
