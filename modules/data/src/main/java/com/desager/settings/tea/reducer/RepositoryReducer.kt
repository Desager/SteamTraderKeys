package com.desager.settings.tea.reducer

import com.desager.arch.tea.model.MessageBuilder
import com.desager.arch.tea.reducer.Reducer
import com.desager.settings.model.DataState
import com.desager.settings.tea.command.RepositoryCommand
import com.desager.settings.tea.event.RepositoryEvent
import com.desager.settings.tea.state.RepositoryState

internal class RepositoryReducer : Reducer<RepositoryState, RepositoryEvent, Unit, RepositoryCommand>() {

    override fun MessageBuilder<RepositoryState, Unit, RepositoryCommand>.reduce(
        event: RepositoryEvent
    ) {
        when (event) {
            is RepositoryEvent.Internal -> handleInternalCommand(event)
            is RepositoryEvent.User -> handleUserCommand(event)
        }
    }

    private fun MessageBuilder<RepositoryState, Unit, RepositoryCommand>.handleInternalCommand(event: RepositoryEvent.Internal) {
        when (event) {
            is RepositoryEvent.Internal.LoadingComplete<*> -> state {
                copy(dataState = DataState.Content(event.data))
            }
            is RepositoryEvent.Internal.LoadingFailed -> state {
                copy(dataState = DataState.Error(event.reason))
            }
        }
    }

    private fun MessageBuilder<RepositoryState, Unit, RepositoryCommand>.handleUserCommand(event: RepositoryEvent.User) {
        when (event) {
            is RepositoryEvent.User.Invalidate -> {
                commands(RepositoryCommand.Invalidate(event.args))
                state {
                    copy(dataState = DataState.Loading)
                }
            }
        }
    }
}