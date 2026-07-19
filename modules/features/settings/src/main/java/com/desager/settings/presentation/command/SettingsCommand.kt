package com.desager.settings.presentation.command

internal sealed interface SettingsCommand {

    object GetApiKey : SettingsCommand

    class SaveApiKey(val apiKey: String) : SettingsCommand

    object DeleteApiKey : SettingsCommand
}