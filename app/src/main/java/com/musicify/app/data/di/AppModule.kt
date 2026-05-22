package com.musicify.app.data.di

import com.musicify.app.data.api.ApiConfig
import com.musicify.app.data.api.LrcLibApi
import com.musicify.app.data.api.PipedApi
import com.musicify.app.data.api.innertube.InnerTubeApi
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
    @Named("innertube")
    fun provideInnerTubeRetrofit(client: OkHttpClient): Retrofit {
        val innerTubeClient = client.newBuilder()
            .addInterceptor { chain ->
                val original = chain.request()
                val isPlayerRequest = original.url.encodedPath.contains("player")
                val builder = original.newBuilder()
                if (isPlayerRequest) {
                    builder.header("User-Agent", "com.google.android.apps.youtube.music/7.27.52 (Linux; U; Android 14; en_US) gzip")
                    builder.header("X-YouTube-Client-Name", "21")
                    builder.header("X-YouTube-Client-Version", "7.27.52")
                }
                chain.proceed(builder.build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://music.youtube.com/youtubei/v1/")
            .client(innerTubeClient)
            .addConverterFactory(GsonConverterFactory.create())
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
    fun provideInnerTubeApi(@Named("innertube") retrofit: Retrofit): InnerTubeApi {
        return retrofit.create(InnerTubeApi::class.java)
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
