package com.desager.settings.model

sealed interface DataState<out T> {

    object Empty : DataState<Nothing>

    object Loading : DataState<Nothing>

    data class Content<T>(val content: T) : DataState<T>

    data class Error(val reason: Throwable) : DataState<Nothing>
}

fun <T, R> DataState<T>.mapContent(mapper: (T) -> R): DataState<R> {
    return when (this) {
        is DataState.Content -> DataState.Content(mapper(content))
        is DataState.Loading -> this
        is DataState.Error -> this
        is DataState.Empty -> this
    }
}