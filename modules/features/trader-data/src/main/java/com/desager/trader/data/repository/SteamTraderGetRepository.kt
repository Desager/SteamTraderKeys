package com.desager.trader.data.repository

import com.desager.settings.repository.SourceRepository
import com.desager.trader.data.api.SteamTraderApi
import com.desager.trader.data.model.ApiKeyArg
import com.desager.trader.data.model.Constants
import com.desager.trader.data.model.dto.steam_trader.SearchItemResponse

class SteamTraderGetRepository(
    private val api: SteamTraderApi,
) : SourceRepository<SearchItemResponse>(
    source = { args ->
        val apiKeyArg = args as ApiKeyArg

        api.searchItemByHashName(
            apiKey = apiKeyArg.apiKey,
            gameId = Constants.APP_ID,
            hashName = Constants.MARKET_HASH_NAME,
            includeDescription = false,
        )
    }
)