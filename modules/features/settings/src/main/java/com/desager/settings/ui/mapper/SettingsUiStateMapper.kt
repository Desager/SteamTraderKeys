package com.desager.settings.ui.mapper

import com.desager.arch.tea.mapper.UiStateMapper
import com.desager.settings.presentation.state.SettingsState as State
import com.desager.settings.ui.state.SettingsUiState as UiState

internal class SettingsUiStateMapper : UiStateMapper<State, UiState> {

    override fun map(state: State): UiState {
        return UiState(
            apiKey = state.apiKey
        )
    }
}