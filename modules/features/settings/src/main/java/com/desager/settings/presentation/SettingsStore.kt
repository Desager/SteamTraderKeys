package com.desager.settings.presentation

import com.desager.arch.mvi.MviStore
import com.desager.arch.tea.factory.TeaStore
import com.desager.common.data.repository.ApiKeyRepository
import com.desager.settings.presentation.action.SettingsAction
import com.desager.settings.presentation.actor.DeleteApiKeyActor
import com.desager.settings.presentation.actor.GetApiKeyActor
import com.desager.settings.presentation.actor.SaveApiKeyActor
import com.desager.settings.presentation.event.SettingsEvent
import com.desager.settings.presentation.reducer.SettingsReducer
import com.desager.settings.presentation.state.SettingsState

internal typealias SettingsStore = MviStore<SettingsState, SettingsEvent, SettingsAction>

internal fun createSettingsStore(
    apiKeyRepository: ApiKeyRepository,
): SettingsStore {
    return TeaStore(
        initialState = SettingsState(),
        reducer = SettingsReducer(),
        actors = listOf(
            GetApiKeyActor(apiKeyRepository),
            SaveApiKeyActor(apiKeyRepository),
            DeleteApiKeyActor(apiKeyRepository),
        )
    )
}