package com.desager.settings.tea

import com.desager.arch.mvi.MviStore
import com.desager.arch.tea.factory.TeaStore
import com.desager.settings.model.Arguments
import com.desager.settings.tea.actor.RepositoryActor
import com.desager.settings.tea.event.RepositoryEvent
import com.desager.settings.tea.reducer.RepositoryReducer
import com.desager.settings.tea.state.RepositoryState

internal typealias RepositoryStore = MviStore<RepositoryState, RepositoryEvent, Unit>

internal fun<T> RepositoryStore(
    source: suspend (Arguments) -> T
): RepositoryStore = TeaStore(
    initialState = RepositoryState(),
    reducer = RepositoryReducer(),
    actors = listOf(
        RepositoryActor(source)
    )
)