package com.desager.trader.presentation

import com.desager.arch.mvi.MviStore
import com.desager.arch.tea.factory.TeaStore
import com.desager.common.data.repository.ApiKeyRepository
import com.desager.trader.data.repository.SteamGetRepository
import com.desager.trader.data.repository.SteamTraderBuyRepository
import com.desager.trader.data.repository.SteamTraderGetRepository
import com.desager.trader.presentation.action.TraderAction
import com.desager.trader.presentation.actor.GetSteamDataActor
import com.desager.trader.presentation.actor.GetSteamTraderDataActor
import com.desager.trader.presentation.actor.InitSteamDataActor
import com.desager.trader.presentation.actor.InitSteamTraderDataActor
import com.desager.trader.presentation.actor.TradingSteamTraderActor
import com.desager.trader.presentation.event.TraderEvent
import com.desager.trader.presentation.reducer.TraderReducer
import com.desager.trader.presentation.state.TraderState

internal typealias TraderStore = MviStore<TraderState, TraderEvent, TraderAction>

internal fun createTraderStore(
    apiKeyRepository: ApiKeyRepository,
    steamGetRepository: SteamGetRepository,
    steamTraderGetRepository: SteamTraderGetRepository,
    steamTraderBuyRepository: SteamTraderBuyRepository,
): TraderStore {
    return TeaStore(
        initialState = TraderState(),
        reducer = TraderReducer(apiKeyRepository),
        actors = listOf(
            GetSteamDataActor(steamGetRepository),
            InitSteamDataActor(steamGetRepository),
            GetSteamTraderDataActor(steamTraderGetRepository),
            InitSteamTraderDataActor(steamTraderGetRepository),
            TradingSteamTraderActor(steamTraderBuyRepository)
        ),
        initialEvents = listOf(
            TraderEvent.Internal.InitApi,
        )
    )
}
