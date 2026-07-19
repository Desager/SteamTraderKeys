package com.desager.trader.data.model.dto.steam_trader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SteamTraderMarketItem(
    @SerialName("hash_name") val hashName: String,
    @SerialName("gameid") val gameId: Int,
    @SerialName("market_price") val marketPrice: Int?,
    @SerialName("steam_price") val steamPrice: Int?,
)