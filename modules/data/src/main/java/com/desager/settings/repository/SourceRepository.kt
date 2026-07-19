package com.desager.settings.repository

import com.desager.arch.mvi.collection.collectState
import com.desager.settings.model.Arguments
import com.desager.settings.model.DataState
import com.desager.settings.tea.RepositoryStore
import com.desager.settings.tea.event.RepositoryEvent
import com.desager.settings.tea.state.RepositoryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
open class SourceRepository<T>(
    source: suspend (Arguments) -> T,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Repository<T> {

    private val store = RepositoryStore(source)

    private val dataFlow = MutableSharedFlow<DataState<T>>(replay = 1)

    init {
        store.attach(coroutineScope)
        coroutineScope.launch {
            store.collectState(::stateMapper, ::stateRenderer)
        }
    }

    private fun stateRenderer(state: DataState<T>) {
        dataFlow.tryEmit(state)
    }

    private fun stateMapper(state: RepositoryState): DataState<T> = state.dataState as DataState<T>

    override fun get(): Flow<DataState<T>> {
        return dataFlow.onCompletion { coroutineScope.cancel() }
    }

    override fun invalidate(args: Arguments) {
        store.dispatch(RepositoryEvent.User.Invalidate(args))
    }
}