package com.desager.trader.ui.model

sealed interface LogMessage {

    data object BuyingStarted : LogMessage

    data object BuyingStopped : LogMessage

    data class KeyPurchased(
        val spent: Long,
    ) : LogMessage

    data class BuyingError(
        val error: BuyError,
    ) : LogMessage

    data object BuyingFinished : LogMessage
}