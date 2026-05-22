package com.musicify.app.data.di

import com.musicify.app.data.api.ApiConfig
import com.musicify.app.data.api.LrcLibApi
import com.musicify.app.data.api.PipedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", ApiConfig.USER_AGENT)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("piped")
    fun providePipedRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConfig.PIPED_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("lrclib")
    fun provideLrcLibRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConfig.LRCLIB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePipedApi(@Named("piped") retrofit: Retrofit): PipedApi {
        return retrofit.create(PipedApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLrcLibApi(@Named("lrclib") retrofit: Retrofit): LrcLibApi {
        return retrofit.create(LrcLibApi::class.java)
    }
}
