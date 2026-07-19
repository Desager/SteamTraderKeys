package com.desager.trader.data.model.dto.steam_trader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    @SerialName("id") val id: String,
    @SerialName("price") val price: Long,
)