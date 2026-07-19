package com.desager.common.data.api

interface ApiKeyStorage {

    fun saveApiKey(apiKey: String)

    fun getApiKey(): String?

    fun deleteApiKey()
}