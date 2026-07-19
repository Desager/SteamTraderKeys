package com.desager.steamtraderkeys.di.network

import com.desager.steamtraderkeys.di.qualifiers.Steam
import com.desager.steamtraderkeys.di.qualifiers.SteamTrader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @SteamTrader
    @Provides
    @Singleton
    fun provideSteamTraderRetrofit(
        client: OkHttpClient,
        json: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val jsonConverterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl("https://api.steam-trader.net")
            .client(client)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    @Steam
    @Provides
    @Singleton
    fun provideSteamRetrofit(
        client: OkHttpClient,
        json: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val jsonConverterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl("https://steamcommunity.com")
            .client(client)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }
}