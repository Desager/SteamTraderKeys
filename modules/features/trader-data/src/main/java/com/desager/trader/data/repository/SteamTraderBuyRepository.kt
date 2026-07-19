package com.desager.trader.data.repository

import com.desager.trader.data.api.SteamTraderApi
import com.desager.trader.data.model.Constants
import com.desager.trader.data.model.dto.steam_trader.MultiBuyRequest
import com.desager.trader.data.model.dto.steam_trader.MultiBuyResult

class SteamTraderBuyRepository(
    private val api: SteamTraderApi,
) {

    suspend fun buyKey(
        apiKey: String,
        price: Long,
        count: Int
    ): MultiBuyResult {
        val response = api.multiBuy(
            apiKey = apiKey,
            body = MultiBuyRequest(
                gameId = Constants.APP_ID,
                price = price,
                hashName = Constants.MARKET_HASH_NAME,
                count = count,
            )
        )

        return response.data
    }
}