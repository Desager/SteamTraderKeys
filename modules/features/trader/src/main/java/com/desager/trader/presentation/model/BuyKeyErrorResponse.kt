package com.desager.trader.presentation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BuyKeyErrorResponse(
    @SerialName("error") val error: BuyKeyError,
)

@Serializable
internal data class BuyKeyError(
    @SerialName("code") val code: String,
    @SerialName("message") val message: String,
    @SerialName("details") val details: String,
)