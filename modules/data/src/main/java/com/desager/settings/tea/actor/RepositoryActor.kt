package com.desager.settings.tea.actor

import com.desager.arch.tea.actor.Actor
import com.desager.settings.model.Arguments
import com.desager.settings.tea.command.RepositoryCommand
import com.desager.settings.tea.event.RepositoryEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

internal class RepositoryActor<T>(
    private val source: suspend (Arguments) -> T
) : Actor<RepositoryEvent, RepositoryCommand>() {

    override fun process(commandFlow: Flow<RepositoryCommand>): Flow<RepositoryEvent> {
        return commandFlow.filterIsInstance<RepositoryCommand.Invalidate>()
            .mapLatest {
                try {
                    RepositoryEvent.Internal.LoadingComplete(source(it.args))
                } catch (t: Throwable) {
                    RepositoryEvent.Internal.LoadingFailed(t)
                }
            }
    }
}