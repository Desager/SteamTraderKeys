package com.desager.settings.presentation.actor

import com.desager.arch.tea.actor.Actor
import com.desager.common.data.repository.ApiKeyRepository
import com.desager.settings.presentation.command.SettingsCommand
import com.desager.settings.presentation.event.SettingsEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

internal class SaveApiKeyActor(
    private val apiKeyRepository: ApiKeyRepository,
) : Actor<SettingsEvent, SettingsCommand>() {

    override fun process(commandFlow: Flow<SettingsCommand>): Flow<SettingsEvent> {
        return commandFlow.filterIsInstance<SettingsCommand.SaveApiKey>()
            .mapLatest {
                apiKeyRepository.saveApiKey(it.apiKey)

                SettingsEvent.Internal.ApiKeySaved
            }
    }
}