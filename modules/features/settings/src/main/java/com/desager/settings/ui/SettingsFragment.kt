package com.desager.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.desager.arch.android.fragment.BaseFragment
import com.desager.arch.android.fragment.storeHolder
import com.desager.arch.android.storeholder.getValue
import com.desager.common.data.repository.ApiKeyRepository
import com.desager.settings.R
import com.desager.settings.presentation.createSettingsStore
import com.desager.settings.ui.mapper.SettingsUiStateMapper
import com.desager.settings.ui.state.SettingsUiState as UiState
import com.desager.settings.presentation.SettingsStore as Store
import com.desager.settings.presentation.action.SettingsAction as Action
import com.desager.settings.presentation.event.SettingsEvent as Event
import com.desager.settings.presentation.state.SettingsState as State
import com.desager.settings.databinding.FragmentSettingsBinding as Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class SettingsFragment : BaseFragment<Binding, State, Event, Action, Store, UiState>() {

    @Inject
    lateinit var apiKeyRepository: ApiKeyRepository

    override val store: Store by storeHolder {
        createSettingsStore(
            apiKeyRepository
        )
    }

    override val uiStateMapper = SettingsUiStateMapper()

    override fun renderState(state: UiState) {
        with(binding) {
            apiKeyEditText.setText(state.apiKey)
        }
    }

    override fun renderAction(action: Action) {
        when (action) {
            Action.ApiKeySaved -> {
                Toast.makeText(requireContext(), R.string.api_key_saved, Toast.LENGTH_SHORT).show()
            }
            Action.ApiKeyIncorrect -> {
                Toast.makeText(requireContext(), R.string.api_key_incorrect, Toast.LENGTH_SHORT).show()
            }
            Action.ApiKeyDeleted -> {
                Toast.makeText(requireContext(), R.string.api_key_deleted, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun provideView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        _binding = Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveApiKeyButton.setOnClickListener {
            val apiKey = binding.apiKeyEditText.text.toString()

            store.dispatch(Event.User.SaveApiKey(apiKey))
        }
        binding.deleteApiKeyButton.setOnClickListener {
            store.dispatch(Event.User.DeleteApiKey)
        }

        store.dispatch(Event.User.GetApiKey)
    }
}