package com.desager.trader.data.repository

import com.desager.settings.repository.MapSourceRepository
import com.desager.trader.data.api.SteamApi
import com.desager.trader.data.mapper.SteamPriceOverviewMapper
import com.desager.trader.data.model.Constants
import com.desager.trader.data.model.SteamPriceOverview
import com.desager.trader.data.model.dto.steam.SteamPriceOverviewResponse

class SteamGetRepository(
    private val api: SteamApi,
    private val steamPriceOverviewMapper: SteamPriceOverviewMapper,
) : MapSourceRepository<SteamPriceOverviewResponse, SteamPriceOverview>(
    source = {
        api.priceOverview(
            appId = Constants.APP_ID,
            currency = Constants.CURRENCY,
            marketHashName = Constants.MARKET_HASH_NAME,
        )
    },
    mapper = {
        steamPriceOverviewMapper.map(it)
    }
)