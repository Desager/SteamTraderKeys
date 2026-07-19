package com.desager.trader.data.model.dto.steam_trader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultiBuyResult(
    @SerialName("spent") val spent: Long,
    @SerialName("orders") val orders: List<Order>,
)