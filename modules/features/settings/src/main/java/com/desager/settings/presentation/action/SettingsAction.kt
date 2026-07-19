package com.desager.settings.presentation.action

internal sealed interface SettingsAction {

    object ApiKeySaved : SettingsAction

    object ApiKeyIncorrect : SettingsAction

    object ApiKeyDeleted : SettingsAction
}