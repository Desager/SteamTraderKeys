package com.desager.trader.presentation.reducer

import com.desager.arch.tea.model.MessageBuilder
import com.desager.arch.tea.reducer.Reducer
import com.desager.common.data.repository.ApiKeyRepository
import com.desager.settings.model.DataState
import com.desager.trader.data.model.ApiKeyArg
import com.desager.trader.data.model.Constants
import com.desager.trader.presentation.command.TraderCommand.*
import com.desager.trader.ui.model.LogInfo
import com.desager.trader.ui.model.PurchasingStatus
import com.desager.trader.presentation.action.TraderAction as Action
import com.desager.trader.presentation.command.TraderCommand as Command
import com.desager.trader.presentation.event.TraderEvent as Event
import com.desager.trader.presentation.state.TraderState as State

internal class TraderReducer(
    private val apiKeyRepository: ApiKeyRepository,
) : Reducer<State, Event, Action, Command>() {

    override fun MessageBuilder<State, Action, Command>.reduce(
        event: Event
    ) {
        when (event) {
            is Event.Internal -> internalEvent(event)
            is Event.User -> userEvent(event)
        }
    }

    private fun MessageBuilder<State, Action, Command>.internalEvent(event: Event.Internal) {
        when (event) {
            Event.Internal.InitApi -> {
                commands(
                    InitSteamTraderData,
                    InitSteamData,
                )
            }
            is Event.Internal.SteamDataStatus -> steamDataStatusEvent(event)
            is Event.Internal.SteamTraderGetDataStatus -> steamTraderDataStatusEvent(event)
            is Event.Internal.BuyingStarted -> {
                updatePurchasingState(
                    logInfo = event.log,
                    purchased = 0,
                    purchasingStatus = PurchasingStatus.ACTIVE
                )
            }
            is Event.Internal.BuyingStopped -> {
                updatePurchasingState(
                    logInfo = event.log,
                    purchased = null,
                    purchasingStatus = PurchasingStatus.INACTIVE
                )
            }
            is Event.Internal.BuyingProgress -> {
                updatePurchasingState(
                    logInfo = event.log,
                    purchased = event.purchased,
                    purchasingStatus = PurchasingStatus.ACTIVE,
                )
            }
            is Event.Internal.BuyingFinished -> {
                updatePurchasingState(
                    logInfo = event.log,
                    purchased = null,
                    purchasingStatus = PurchasingStatus.INACTIVE
                )
            }
        }
    }

    private fun MessageBuilder<State, Action, Command>.updatePurchasingState(
        logInfo: LogInfo,
        purchased: Int?,
        purchasingStatus: PurchasingStatus,
    ) {
        val newPurchasingLogs = state.purchasingLogs.toMutableList()
        newPurchasingLogs.add(logInfo)
        state {
            copy(
                purchasingStatus = purchasingStatus,
                purchasingLogs = newPurchasingLogs,
                purchased = purchased ?: this.purchased,
            )
        }
    }

    private fun MessageBuilder<State, Action, Command>.steamDataStatusEvent(
        event: Event.Internal.SteamDataStatus
    ) {
        val data = event.data
        when (data) {
            DataState.Empty -> Unit
            DataState.Loading -> Unit
            is DataState.Content -> {
                val newState = state.copy(
                    steamPrice = data.content.lowestPrice
                )
                state {
                    calculateNewData(newState)
                }
            }
            is DataState.Error -> {
                actions(Action.SteamDataFailed(data.reason))
            }
        }
    }

    private fun MessageBuilder<State, Action, Command>.steamTraderDataStatusEvent(
        event: Event.Internal.SteamTraderGetDataStatus
    ) {
        val data = event.data
        when(data) {
            DataState.Empty -> Unit
            DataState.Loading -> Unit
            is DataState.Content -> {
                val steamTraderPrice = data.content.data?.marketPrice ?: 0

                val newState = state.copy(
                    steamTraderPrice = steamTraderPrice / 100.0
                )
                state {
                    calculateNewData(newState)
                }
            }
            is DataState.Error -> {
                actions(Action.SteamTraderDataFailed(data.reason))
            }
        }
    }

    private fun MessageBuilder<State, Action, Command>.userEvent(event: Event.User) {
        when (event) {
            Event.User.DecreaseKeyCount -> {
                if (state.purchasingStatus == PurchasingStatus.ACTIVE) {
                    actions(Action.NeedToStopTrading)
                    return
                }
                if (state.count > 1) {
                    state {
                        calculateNewData(
                            copy(
                                count = count - 1,
                                purchased = 0,
                            )
                        )
                    }
                }
            }
            Event.User.IncreaseKeyCount -> {
                if (state.purchasingStatus == PurchasingStatus.ACTIVE) {
                    actions(Action.NeedToStopTrading)
                    return
                }
                if (state.count < 100) {
                    state {
                        calculateNewData(
                            copy(
                                count = count + 1,
                                purchased = 0,
                            )
                        )
                    }
                }
            }
            Event.User.UpdateData -> {
                val apiKey = apiKeyRepository.getApiKey()
                if (apiKey == null) {
                    actions(Action.SteamTraderApiKeyEmpty)
                    return
                }
                if (state.purchasingStatus == PurchasingStatus.ACTIVE) {
                    actions(Action.NeedToStopTrading)
                    return
                }
                commands(
                    GetSteamData,
                    GetSteamTraderData(ApiKeyArg(apiKey))
                )
            }

            Event.User.StartTradingButtonPressed -> {
                when (state.purchasingStatus) {
                    PurchasingStatus.ACTIVE -> commands(StopBuying)
                    PurchasingStatus.INACTIVE -> {
                        val apiKey = apiKeyRepository.getApiKey()
                        if (apiKey == null) {
                            actions(Action.SteamTraderApiKeyEmpty)
                            return
                        }
                        commands(
                            StartBuying(
                                apiKey = apiKey,
                                count = state.count,
                                price = state.steamTraderPrice
                            )
                        )
                    }
                }
            }

            Event.User.SettingsButtonPressed -> {
                when (state.purchasingStatus) {
                    PurchasingStatus.ACTIVE -> actions(Action.NeedToStopTrading)
                    PurchasingStatus.INACTIVE -> actions(Action.NavigateToSettings)
                }
            }
        }
    }

    private fun calculateNewData(state: State): State {
        val depositAmount = state.steamTraderPrice * state.count
        val payPrice = depositAmount + depositAmount * Constants.STEAM_TRADER_FEE
        val receivePrice = (state.steamPrice - state.steamPrice * Constants.STEAM_FEE) * state.count
        val fee = if (payPrice == 0.0) {
            0.0
        } else {
            (1 - receivePrice / payPrice) * 100
        }
        val difference = state.steamPrice - state.steamTraderPrice
        val result = receivePrice - payPrice

        return state.copy(
            depositAmount = depositAmount,
            payPrice = payPrice,
            receivePrice = receivePrice,
            fee = fee,
            difference = difference,
            result = result,
        )
    }
}