package com.desager.arch.tea.reducer

import com.desager.arch.tea.model.Message
import com.desager.arch.tea.model.MessageBuilder

abstract class Reducer<State : Any, Event : Any, Action : Any, Command : Any> {

    protected abstract fun MessageBuilder<State, Action, Command>.reduce(event: Event)

    internal fun reduce(state: State, event: Event): Message<State, Action, Command> {
        val messageBuilder = MessageBuilder<State, Action, Command>(state)
        messageBuilder.reduce(event)
        return messageBuilder.build()
    }
}