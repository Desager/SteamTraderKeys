package com.desager.trader.presentation.actor

import android.util.Log
import com.desager.arch.tea.actor.Actor
import com.desager.settings.model.Arguments
import com.desager.trader.data.repository.SteamGetRepository
import com.desager.trader.presentation.command.TraderCommand
import com.desager.trader.presentation.event.TraderEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest

internal class GetSteamDataActor(
    private val steamGetRepository: SteamGetRepository,
) : Actor<TraderEvent, TraderCommand>() {

    override fun process(commandFlow: Flow<TraderCommand>): Flow<TraderEvent> {
        return commandFlow.filterIsInstance<TraderCommand.GetSteamData>()
            .mapLatest {
                Log.d(TAG, "GetSteamData received")
                steamGetRepository.invalidate(Arguments.Empty)
            }
            .flatMapMerge { emptyFlow() }
    }

    companion object {
        private const val TAG = "GetSteamDataActor"
    }
}