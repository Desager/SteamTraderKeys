package com.desager.steamtraderkeys.di.features.settings

import com.desager.common.data.api.ApiKeyStorage
import com.desager.steamtraderkeys.data.local.storage.ApiKeyStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StorageBindModule {

    @Binds
    @Singleton
    fun bindApiKeyStorage(
        impl: ApiKeyStorageImpl,
    ): ApiKeyStorage
}