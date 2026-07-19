package com.desager.steamtraderkeys.data.local.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.desager.common.data.api.ApiKeyStorage
import javax.inject.Inject

class ApiKeyStorageImpl @Inject constructor(
    private val prefs: SharedPreferences,
) : ApiKeyStorage {

    override fun saveApiKey(apiKey: String) {
        prefs.edit {
            putString(API_KEY, apiKey)
        }
    }

    override fun getApiKey(): String? {
        return prefs.getString(API_KEY, null)
    }

    override fun deleteApiKey() {
        prefs.edit {
            remove(API_KEY)
        }
    }

    companion object {
        private const val API_KEY = "steam_trader_api_key"
    }
}