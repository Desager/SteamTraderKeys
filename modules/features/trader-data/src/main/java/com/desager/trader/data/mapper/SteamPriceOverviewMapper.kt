package com.desager.trader.data.mapper

import com.desager.trader.data.model.SteamPriceOverview
import com.desager.trader.data.model.dto.steam.SteamPriceOverviewResponse

class SteamPriceOverviewMapper {

    fun map(model: SteamPriceOverviewResponse): SteamPriceOverview {
        return SteamPriceOverview(
            lowestPrice = model.lowestPrice
                .replace(" руб.", "")
                .replace(",", ".")
                .toDouble(),

            medianPrice = model.medianPrice
                .replace(" руб.", "")
                .replace(",", ".")
                .toDouble(),
        )
    }
}