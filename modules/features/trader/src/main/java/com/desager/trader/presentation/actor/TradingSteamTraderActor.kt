package com.desager.trader.presentation.actor

import com.desager.arch.tea.actor.Actor
import com.desager.trader.data.model.Constants
import com.desager.trader.data.repository.SteamTraderBuyRepository
import com.desager.trader.presentation.command.TraderCommand
import com.desager.trader.presentation.event.TraderEvent
import com.desager.trader.presentation.model.BuyKeyErrorResponse
import com.desager.trader.ui.model.BuyError
import com.desager.trader.ui.model.LogInfo
import com.desager.trader.ui.model.LogMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import kotlin.math.roundToLong
import kotlin.time.Duration.Companion.milliseconds

internal class TradingSteamTraderActor(
    private val steamTraderBuyRepository: SteamTraderBuyRepository,
) : Actor<TraderEvent, TraderCommand>() {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun process(commandFlow: Flow<TraderCommand>): Flow<TraderEvent> {
        return commandFlow
            .filter { command ->
                command is TraderCommand.StartBuying ||
                        command is TraderCommand.StopBuying
            }
            .flatMapLatest { command ->
                when (command) {
                    is TraderCommand.StartBuying -> buyKeys(command)

                    is TraderCommand.StopBuying -> flowOf(
                        TraderEvent.Internal.BuyingStopped(
                            log = LogInfo.warning(
                                LogMessage.BuyingStopped
                            )
                        )
                    )

                    else -> emptyFlow()
                }
            }
    }

    private fun buyKeys(command: TraderCommand.StartBuying): Flow<TraderEvent.Internal> = flow {
        var purchased = 0

        emit(
            TraderEvent.Internal.BuyingStarted(
                log = LogInfo.success(
                    LogMessage.BuyingStarted
                )
            )
        )

        while (purchased < command.count) {
            val log = try {
                val result = steamTraderBuyRepository.buyKey(
                    apiKey = command.apiKey,
                    price = (command.price * 100).roundToLong(),
                    count = 1
                )

                purchased++
                LogInfo.success(
                    LogMessage.KeyPurchased(result.spent)
                )
            } catch (e: HttpException) {
                val error = parseBuyError(
                    e.response()?.errorBody()?.string()
                )

                LogInfo.error(
                    LogMessage.BuyingError(error)
                )
            }

            emit(
                TraderEvent.Internal.BuyingProgress(
                    log = log,
                    purchased = purchased
                )
            )

            delay(Constants.DELAY_BETWEEN_REQUESTS_MLS.milliseconds)
        }

        emit(
            TraderEvent.Internal.BuyingFinished(
                log = LogInfo.success(
                    LogMessage.BuyingFinished
                )
            )
        )
    }

    private fun parseBuyError(errorBody: String?): BuyError {
        if (errorBody == null) {
            return BuyError.Unknown(details = null)
        }

        return runCatching {
            val response = json.decodeFromString<BuyKeyErrorResponse>(errorBody)
            val details = response.error.details

            when {
                details.contains("BalanceNotEnough") -> BuyError.BalanceNotEnough
                details.contains("The data passed is not correct") -> BuyError.DataPassedNotCorrect
                else -> BuyError.Unknown(
                    details = response.error.details
                )
            }
        }.getOrElse {
            BuyError.Unknown(details = null)
        }
    }
}