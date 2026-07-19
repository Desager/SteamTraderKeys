package com.desager.arch.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow

abstract class MviStore<State : Any, Event : Any, Action : Any>(
    initialState: State,
) {

    private var _coroutineScope: CoroutineScope? = null
    protected val coroutineScope
        get() = _coroutineScope ?: error("Store is not attached")

    protected val _stateFlow = MutableStateFlow(initialState)
    val stateFlow get() = _stateFlow.asStateFlow()

    protected val _eventChannel = Channel<Event>(Channel.BUFFERED)
    internal val eventFlow = _eventChannel.consumeAsFlow()

    protected val _actionFlow = MutableSharedFlow<Action>()
    val actionFlow get() = _actionFlow.asSharedFlow()

    open fun attach(coroutineScope: CoroutineScope) {
        if (_coroutineScope != null) error("Store is already attached")

        _coroutineScope = coroutineScope
    }

    fun dispatch(event: Event) {
        _eventChannel.trySend(event)
    }
}