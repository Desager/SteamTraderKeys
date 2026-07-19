package com.desager.steamtraderkeys.di.features.common

import com.desager.common.data.api.ApiKeyStorage
import com.desager.common.data.repository.ApiKeyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonDataModule {

    @Provides
    @Singleton
    fun provideApiKeyRepository(
        apiKeyStorage: ApiKeyStorage,
    ): ApiKeyRepository {
        return ApiKeyRepository(apiKeyStorage)
    }
}