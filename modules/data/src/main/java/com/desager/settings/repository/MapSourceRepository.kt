package com.desager.settings.repository

import com.desager.settings.model.Arguments
import com.desager.settings.model.DataState
import com.desager.settings.model.mapContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class MapSourceRepository<T, R>(
    source: suspend (Arguments) -> T,
    private val mapper: (T) -> R,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Repository<R> {

    private val sourceRepo = SourceRepository(
        source = source,
        coroutineScope = coroutineScope
    )

    override fun get(): Flow<DataState<R>> {
        return sourceRepo.get()
            .map { it.mapContent(mapper) }
    }

    override fun invalidate(args: Arguments) {
        sourceRepo.invalidate(args)
    }
}