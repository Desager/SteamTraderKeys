package com.desager.trader.ui.model

internal sealed interface BuyError {

    data object BalanceNotEnough : BuyError

    data object DataPassedNotCorrect : BuyError

    data class Unknown(
        val details: String?,
    ) : BuyError
}