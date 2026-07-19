package com.desager.trader.data.api

import com.desager.trader.data.model.dto.steam_trader.MultiBuyRequest
import com.desager.trader.data.model.dto.steam_trader.MultiBuyResponse
import com.desager.trader.data.model.dto.steam_trader.SearchItemResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SteamTraderApi {

    @GET("/v1/market/search-item-by-hash-name")
    suspend fun searchItemByHashName(
        @Header("X-API-Key") apiKey: String,
        @Query("gameid") gameId: Int,
        @Query("hashname") hashName: String,
        @Query("includedescription") includeDescription: Boolean,
    ): SearchItemResponse

    @POST("/v1/market/multi-buy")
    suspend fun multiBuy(
        @Header("X-API-Key") apiKey: String,
        @Body body: MultiBuyRequest,
    ): MultiBuyResponse
}