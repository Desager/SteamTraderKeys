package com.desager.arch.tea.model

internal data class Message<State : Any, Action : Any, Command : Any>(
    val state: State,
    val actions: List<Action>,
    val commands: List<Command>,
)