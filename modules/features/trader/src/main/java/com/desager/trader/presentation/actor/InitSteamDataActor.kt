package com.desager.trader.presentation.actor

import android.util.Log
import com.desager.arch.tea.actor.Actor
import com.desager.trader.data.repository.SteamGetRepository
import com.desager.trader.presentation.command.TraderCommand
import com.desager.trader.presentation.event.TraderEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

internal class InitSteamDataActor(
    private val steamGetRepository: SteamGetRepository,
) : Actor<TraderEvent, TraderCommand>() {

    override fun process(commandFlow: Flow<TraderCommand>): Flow<TraderEvent> {
        return commandFlow.filterIsInstance<TraderCommand.InitSteamData>()
            .flatMapLatest {
                Log.d(TAG, "InitSteamData received")
                steamGetRepository.get()
            }
            .mapLatest { TraderEvent.Internal.SteamDataStatus(it) }
    }

    companion object {
        private const val TAG = "InitSteamDataActor"
    }
}