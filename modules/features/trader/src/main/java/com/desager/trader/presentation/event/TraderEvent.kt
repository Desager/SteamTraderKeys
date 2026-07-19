package com.desager.trader.presentation.event

import com.desager.settings.model.DataState
import com.desager.trader.data.model.SteamPriceOverview
import com.desager.trader.data.model.dto.steam_trader.SearchItemResponse
import com.desager.trader.ui.model.LogInfo

internal sealed interface TraderEvent {

    sealed interface Internal : TraderEvent {

        class SteamDataStatus(val data: DataState<SteamPriceOverview>) : Internal

        class SteamTraderGetDataStatus(val data: DataState<SearchItemResponse>) : Internal

        object InitApi : Internal

        class BuyingStarted(val log: LogInfo) : Internal

        class BuyingStopped(val log: LogInfo) : Internal

        class BuyingProgress(
            val log: LogInfo,
            val purchased: Int,
        ) : Internal

        class BuyingFinished(val log: LogInfo) : Internal
    }

    sealed interface User : TraderEvent {

        object UpdateData : User

        object IncreaseKeyCount : User

        object DecreaseKeyCount : User

        object StartTradingButtonPressed : User

        object SettingsButtonPressed : User
    }
}