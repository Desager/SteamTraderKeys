package com.desager.arch.tea.factory

import com.desager.arch.mvi.MviStore
import com.desager.arch.tea.TeaStoreImpl
import com.desager.arch.tea.actor.Actor
import com.desager.arch.tea.reducer.Reducer

fun <State : Any, Event : Any, Action : Any, Command : Any> TeaStore(
    initialState: State,
    reducer: Reducer<State, Event, Action, Command>,
    actors: List<Actor<Event, Command>> = listOf(),
    initialEvents: List<Event> = listOf()
): MviStore<State, Event, Action> {
    return TeaStoreImpl(
        initialState = initialState,
        reducer = reducer,
        actors = actors,
        initialEvents = initialEvents,
    )
}