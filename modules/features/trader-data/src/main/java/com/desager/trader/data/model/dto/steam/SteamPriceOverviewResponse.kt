package com.desager.trader.data.model.dto.steam

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SteamPriceOverviewResponse(
    @SerialName("lowest_price") val lowestPrice: String,
    @SerialName("median_price") val medianPrice: String,
)