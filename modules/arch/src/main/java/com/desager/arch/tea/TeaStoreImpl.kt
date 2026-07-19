package com.desager.arch.tea

import com.desager.arch.mvi.MviStore
import com.desager.arch.tea.actor.Actor
import com.desager.arch.tea.reducer.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class TeaStoreImpl<State : Any, Event : Any, Action : Any, Command : Any>(
    initialState: State,
    private val reducer: Reducer<State, Event, Action, Command>,
    private val actors: List<Actor<Event, Command>> = listOf(),
    private val initialEvents: List<Event> = listOf(),
) : MviStore<State, Event, Action>(initialState) {

    private val commandFlow = MutableSharedFlow<Command>(
        replay = 0,
        extraBufferCapacity = 64,
    )

    override fun attach(coroutineScope: CoroutineScope) {
        super.attach(coroutineScope)

        startActors()
        startEventsFlow()
        startInitialEvents()
    }

    private fun startEventsFlow() {
        coroutineScope.launch(start = CoroutineStart.UNDISPATCHED) {
            eventFlow
                .map { event ->
                    val state = stateFlow.value
                    reducer.reduce(state, event)
                }
                .collect { message ->
                    _stateFlow.emit(message.state)

                    message.actions.forEach { action ->
                        _actionFlow.emit(action)
                    }

                    message.commands.forEach { command ->
                        commandFlow.emit(command)
                    }
                }
        }
    }

    private fun startActors() {
        actors.forEach { actor ->
            coroutineScope.launch(start = CoroutineStart.UNDISPATCHED) {
                actor.process(commandFlow)
                    .collect(_eventChannel::send)
            }
        }
    }

    private fun startInitialEvents() {
        coroutineScope.launch {
            initialEvents.forEach { event ->
                dispatch(event)
            }
        }
    }
}