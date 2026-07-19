package com.desager.arch.tea.mapper

fun interface UiStateMapper<State : Any, UiState : Any> {

    fun map(state: State): UiState
}