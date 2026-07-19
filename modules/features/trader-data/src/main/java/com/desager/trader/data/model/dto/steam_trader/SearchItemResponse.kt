package com.desager.trader.data.model.dto.steam_trader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchItemResponse(
    @SerialName("data") val data: SteamTraderMarketItem?,
    @SerialName("status") val status: String,
)