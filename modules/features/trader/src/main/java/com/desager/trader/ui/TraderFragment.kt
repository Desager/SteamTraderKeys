package com.desager.trader.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.desager.arch.android.fragment.BaseFragment
import com.desager.arch.android.fragment.storeHolder
import com.desager.arch.android.storeholder.getValue
import com.desager.common.data.repository.ApiKeyRepository
import com.desager.common.ui.adapter.FingerprintAdapter
import com.desager.trader.R
import com.desager.trader.data.repository.SteamGetRepository
import com.desager.trader.data.repository.SteamTraderBuyRepository
import com.desager.trader.data.repository.SteamTraderGetRepository
import com.desager.trader.presentation.createTraderStore
import com.desager.trader.ui.fingerprints.LogItemFingerprint
import com.desager.trader.ui.mapper.TraderUiStateMapper
import com.desager.trader.ui.model.PurchasingStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.desager.trader.databinding.FragmentTraderBinding as Binding
import com.desager.trader.presentation.TraderStore as Store
import com.desager.trader.presentation.action.TraderAction as Action
import com.desager.trader.presentation.event.TraderEvent as Event
import com.desager.trader.presentation.state.TraderState as State
import com.desager.trader.ui.state.TraderUiState as UiState

@AndroidEntryPoint
internal class TraderFragment : BaseFragment<Binding, State, Event, Action, Store, UiState>() {

    @Inject
    lateinit var apiKeyRepository: ApiKeyRepository

    @Inject
    lateinit var steamGetRepository: SteamGetRepository

    @Inject
    lateinit var steamTraderGetRepository: SteamTraderGetRepository

    @Inject
    lateinit var steamTraderBuyRepository: SteamTraderBuyRepository

    override val store: Store by storeHolder {
        createTraderStore(
            apiKeyRepository,
            steamGetRepository,
            steamTraderGetRepository,
            steamTraderBuyRepository,
        )
    }

    override val uiStateMapper = TraderUiStateMapper()

    private val adapter = FingerprintAdapter(
        fingerprints = listOf(
            LogItemFingerprint()
        )
    )

    override fun renderState(state: UiState) {
        with(binding) {
            steamTraderPriceTextView.text = getString(
                R.string.price_format,
                state.steamTraderPrice
            )
            steamPriceTextView.text = getString(
                R.string.price_format,
                state.steamPrice
            )
            feePercentTextView.text = getString(
                R.string.percent_format,
                state.fee
            )
            differenceTextView.text = getString(
                R.string.price_format,
                state.difference
            )
            keysCountTextView.text = state.count.toString()
            totalPayTextView.text = getString(
                R.string.price_format,
                state.payPrice
            )
            totalReceiveTextView.text = getString(
                R.string.price_format,
                state.receivePrice
            )
            resultTextView.text = getString(
                R.string.price_format,
                state.result
            )
            depositTextView.text = getString(
                R.string.price_format,
                state.depositAmount
            )
            boughtCountTextView.text = getString(
                R.string.progress_format,
                state.purchased.toString(),
                state.count.toString()
            )
            buyProgressBar.max = state.count
            buyProgressBar.setProgress(state.purchased, true)
            statusContentTextView.text = getPurchasingStatusText(state.purchasingStatus)
            adapter.submitList(state.purchasingLogs)
            logsRecyclerView.post {
                logsRecyclerView.layoutManager!!.scrollToPosition(state.purchasingLogs.size - 1)
            }
            updatePurchasingButton(state.purchasingStatus)
        }
    }

    private fun getPurchasingStatusText(status: PurchasingStatus): String {
        return when (status) {
            PurchasingStatus.ACTIVE -> getString(R.string.active)
            PurchasingStatus.INACTIVE -> getString(R.string.inactive)
        }
    }

    private fun Binding.updatePurchasingButton(status: PurchasingStatus) {
        when (status) {
            PurchasingStatus.ACTIVE -> {
                buyButton.text = getString(R.string.stop_buying)
                buyButton.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), com.desager.common.ui.R.color.red_700)
                )
            }
            PurchasingStatus.INACTIVE -> {
                buyButton.text = getString(R.string.start_buying)
                buyButton.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), com.desager.common.ui.R.color.green_700)
                )
            }
        }
    }

    override fun renderAction(action: Action) {
        when (action) {
            is Action.SteamDataFailed -> {
                Log.d(TAG, action.reason.message, action.reason)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.steam_data_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Action.SteamTraderApiKeyEmpty -> {
                Toast.makeText(requireContext(), R.string.api_key_empty, Toast.LENGTH_SHORT).show()
            }
            is Action.SteamTraderDataFailed -> {
                Log.d(TAG, action.reason.message, action.reason)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.steam_trader_data_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Action.NeedToStopTrading -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.need_to_stop_trading),
                    Toast.LENGTH_SHORT
                ).show()
            }

            Action.NavigateToSettings -> navController.navigate(R.id.settingsFragment)
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

        setupMenu()

        binding.updateButton.setOnClickListener {
            store.dispatch(Event.User.UpdateData)
        }
        binding.buyButton.setOnClickListener {
            store.dispatch(Event.User.StartTradingButtonPressed)
        }
        binding.increaseCountButton.setOnClickListener {
            store.dispatch(Event.User.IncreaseKeyCount)
        }
        binding.decreaseCountButton.setOnClickListener {
            store.dispatch(Event.User.DecreaseKeyCount)
        }
        binding.logsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TraderFragment.adapter
            itemAnimator = null
        }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(
            object : MenuProvider {

                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_trader, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.settingsFragment -> {
                            store.dispatch(Event.User.SettingsButtonPressed)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED,
        )
    }

    companion object {
        private const val TAG = "TraderFragment"
    }
}