package com.desager.trader.presentation.action

internal sealed interface TraderAction {

    class SteamDataFailed(val reason: Throwable) : TraderAction

    class SteamTraderDataFailed(val reason: Throwable) : TraderAction

    object SteamTraderApiKeyEmpty : TraderAction

    object NeedToStopTrading : TraderAction

    object NavigateToSettings : TraderAction
}