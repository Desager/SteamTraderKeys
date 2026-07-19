package com.desager.trader.presentation.actor

import android.util.Log
import com.desager.arch.tea.actor.Actor
import com.desager.trader.data.repository.SteamTraderGetRepository
import com.desager.trader.presentation.command.TraderCommand
import com.desager.trader.presentation.event.TraderEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

internal class GetSteamTraderDataActor(
    private val steamTraderGetRepository: SteamTraderGetRepository,
) : Actor<TraderEvent, TraderCommand>() {

    override fun process(commandFlow: Flow<TraderCommand>): Flow<TraderEvent> {
        return commandFlow.filterIsInstance<TraderCommand.GetSteamTraderData>()
            .mapLatest {
                Log.d(TAG, "GetSteamTraderData received")
                steamTraderGetRepository.invalidate(it.apiKeyArg)
            }
            .flatMapLatest { emptyFlow() }
    }

    companion object {
        private const val TAG = "GetSteamTraderDataActor"
    }
}