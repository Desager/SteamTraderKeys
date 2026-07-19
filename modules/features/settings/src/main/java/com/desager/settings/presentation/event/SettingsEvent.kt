package com.desager.settings.presentation.event

internal sealed interface SettingsEvent {

    sealed interface Internal : SettingsEvent {

        class ApiKeyContent(val apiKey: String) : Internal

        object ApiKeySaved : Internal

        object ApiKeyDeleted : Internal
    }

    sealed interface User : SettingsEvent {

        object GetApiKey : User

        class SaveApiKey(val apiKey: String) : User

        object DeleteApiKey : User
    }
}