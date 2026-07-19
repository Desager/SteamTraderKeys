package com.desager.trader.data.model.dto.steam_trader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultiBuyResponse(
    @SerialName("data") val data: MultiBuyResult,
    @SerialName("status") val status: String,
)