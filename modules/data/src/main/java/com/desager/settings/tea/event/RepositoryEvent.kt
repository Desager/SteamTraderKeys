package com.desager.settings.tea.event

import com.desager.settings.model.Arguments

internal sealed interface RepositoryEvent {

    sealed interface Internal : RepositoryEvent {

        class LoadingComplete<T>(val data: T) : Internal

        class LoadingFailed(val reason: Throwable) : Internal
    }

    sealed interface User : RepositoryEvent {

        class Invalidate(val args: Arguments) : User
    }
}



