package com.muthiani.movieswatchpro.di

import com.muthiani.movieswatchpro.data.MoviesWatchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
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
}
