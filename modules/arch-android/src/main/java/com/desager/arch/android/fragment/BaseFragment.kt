package com.desager.arch.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.desager.arch.mvi.MviStore
import com.desager.arch.mvi.collection.collectAction
import com.desager.arch.mvi.collection.collectState
import com.desager.arch.tea.mapper.UiStateMapper
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, State : Any, Event : Any, Action : Any, S : MviStore<State, Event, Action>, UiState : Any> : Fragment() {

    protected var _binding: VB? = null
    protected val binding get() = _binding!!

    protected abstract fun renderState(state: UiState)
    protected abstract fun renderAction(action: Action)

    protected abstract val store: S
    protected abstract val uiStateMapper: UiStateMapper<State, UiState>

    protected val navController by lazy { findNavController() }

    protected abstract fun provideView(inflater: LayoutInflater, container: ViewGroup?): View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = provideView(inflater, container)

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    store.collectState(uiStateMapper, ::renderState)
                }
                launch {
                    store.collectAction(::renderAction)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}