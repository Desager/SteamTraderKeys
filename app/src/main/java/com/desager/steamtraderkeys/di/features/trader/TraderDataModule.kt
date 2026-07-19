package com.desager.steamtraderkeys.di.features.trader

import com.desager.steamtraderkeys.di.qualifiers.Steam
import com.desager.steamtraderkeys.di.qualifiers.SteamTrader
import com.desager.trader.data.api.SteamApi
import com.desager.trader.data.api.SteamTraderApi
import com.desager.trader.data.mapper.SteamPriceOverviewMapper
import com.desager.trader.data.repository.SteamGetRepository
import com.desager.trader.data.repository.SteamTraderBuyRepository
import com.desager.trader.data.repository.SteamTraderGetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TraderDataModule {

    @Provides
    @Singleton
    fun provideSteamApi(
        @Steam retrofit: Retrofit,
    ): SteamApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideSteamPriceOverviewMapper(): SteamPriceOverviewMapper {
        return SteamPriceOverviewMapper()
    }

    @Provides
    @Singleton
    fun provideSteamGetRepository(
        api: SteamApi,
        steamPriceOverviewMapper: SteamPriceOverviewMapper,
    ): SteamGetRepository {
        return SteamGetRepository(api, steamPriceOverviewMapper)
    }

    @Provides
    @Singleton
    fun provideSteamTraderApi(
        @SteamTrader retrofit: Retrofit,
    ): SteamTraderApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideSteamTraderGetRepository(
        api: SteamTraderApi
    ): SteamTraderGetRepository {
        return SteamTraderGetRepository(api)
    }

    @Provides
    @Singleton
    fun provideSteamTraderBuyRepository(
        api: SteamTraderApi
    ): SteamTraderBuyRepository {
        return SteamTraderBuyRepository(api)
    }
}