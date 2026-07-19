package com.desager.settings.presentation.reducer

import com.desager.arch.tea.model.MessageBuilder
import com.desager.arch.tea.reducer.Reducer
import com.desager.settings.presentation.action.SettingsAction as Action
import com.desager.settings.presentation.command.SettingsCommand as Command
import com.desager.settings.presentation.event.SettingsEvent as Event
import com.desager.settings.presentation.state.SettingsState as State

internal class SettingsReducer : Reducer<State, Event, Action, Command>() {

    override fun MessageBuilder<State, Action, Command>.reduce(
        event: Event
    ) {
        when (event) {
            is Event.Internal -> internalEvent(event)
            is Event.User -> userEvent(event)
        }
    }

    private fun MessageBuilder<State, Action, Command>.internalEvent(event: Event.Internal) {
        when (event) {
            is Event.Internal.ApiKeyContent -> state { copy(apiKey = event.apiKey) }
            Event.Internal.ApiKeySaved -> actions(Action.ApiKeySaved)
            Event.Internal.ApiKeyDeleted -> {
                state { copy(apiKey = "") }
                actions(Action.ApiKeyDeleted)
            }
        }
    }

    private fun MessageBuilder<State, Action, Command>.userEvent(event: Event.User) {
        when (event) {
            Event.User.GetApiKey -> commands(Command.GetApiKey)
            is Event.User.SaveApiKey -> {
                val trimmedApiKey = event.apiKey.trim()
                if (trimmedApiKey.isEmpty()) {
                    actions(Action.ApiKeyIncorrect)
                } else {
                    commands(Command.SaveApiKey(trimmedApiKey))
                }
            }
            Event.User.DeleteApiKey -> commands(Command.DeleteApiKey)
        }
    }
}