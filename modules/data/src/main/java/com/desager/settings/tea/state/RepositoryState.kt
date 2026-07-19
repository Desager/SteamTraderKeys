package com.desager.settings.tea.state

import com.desager.settings.model.DataState

internal data class RepositoryState(
    val dataState: DataState<*> = DataState.Empty
)
