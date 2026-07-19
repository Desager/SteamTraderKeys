package com.desager.arch.mvi.collection

import com.desager.arch.mvi.MviStore
import com.desager.arch.tea.mapper.UiStateMapper
import kotlinx.coroutines.flow.map

suspend inline fun <State : Any, UiState : Any> MviStore<State, *, *>.collectState(
    stateMapper: UiStateMapper<State, UiState>,
    crossinline stateRender: (UiState) -> Unit
) {
    stateFlow
        .map(stateMapper::map)
        .collect { stateRender(it) }
}

suspend inline fun <Action : Any> MviStore<*, *, Action>.collectAction(
    crossinline actionRender: (Action) -> Unit
) {
    actionFlow
        .collect { actionRender(it) }
}