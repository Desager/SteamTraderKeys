package com.desager.trader.data.model.dto.steam_trader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultiBuyRequest(
    @SerialName("gameid") val gameId: Int,
    @SerialName("price") val price: Long,
    @SerialName("hashname") val hashName: String,
    @SerialName("count") val count: Int,
)