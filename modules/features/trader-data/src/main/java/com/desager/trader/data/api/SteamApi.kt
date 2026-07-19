package com.desager.trader.data.api

import com.desager.trader.data.model.dto.steam.SteamPriceOverviewResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApi {

    @GET("market/priceoverview")
    suspend fun priceOverview(
        @Query("appid") appId: Int,
        @Query("currency") currency: Int,
        @Query("market_hash_name") marketHashName: String,
    ): SteamPriceOverviewResponse
}