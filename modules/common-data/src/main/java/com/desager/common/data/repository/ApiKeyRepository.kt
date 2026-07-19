package com.desager.common.data.repository

import com.desager.common.data.api.ApiKeyStorage

class ApiKeyRepository(
    private val apiKeyStorage: ApiKeyStorage,
) {

    fun saveApiKey(apiKey: String) {
        apiKeyStorage.saveApiKey(apiKey)
    }

    fun getApiKey(): String? {
        return apiKeyStorage.getApiKey()
    }

    fun deleteApiKey() {
        apiKeyStorage.deleteApiKey()
    }
}