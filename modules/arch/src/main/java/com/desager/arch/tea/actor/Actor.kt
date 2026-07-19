package com.desager.arch.tea.actor

import kotlinx.coroutines.flow.Flow

abstract class Actor<Event : Any, Command : Any> {

    abstract fun process(commandFlow: Flow<Command>): Flow<Event>
}