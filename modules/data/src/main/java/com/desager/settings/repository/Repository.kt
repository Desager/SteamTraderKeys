package com.desager.settings.repository

import com.desager.settings.model.Arguments
import com.desager.settings.model.DataState
import kotlinx.coroutines.flow.Flow

interface Repository<T> {

    fun get(): Flow<DataState<T>>

    fun invalidate(args: Arguments = Arguments.Empty)
}