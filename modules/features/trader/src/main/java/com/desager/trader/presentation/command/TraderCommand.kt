package com.desager.trader.presentation.command

import com.desager.trader.data.model.ApiKeyArg

internal sealed interface TraderCommand {

    object GetSteamData : TraderCommand

    class GetSteamTraderData(val apiKeyArg: ApiKeyArg) : TraderCommand

    object InitSteamData : TraderCommand

    object InitSteamTraderData : TraderCommand

    class StartBuying(
        val apiKey: String,
        val price: Double,
        val count: Int,
    ) : TraderCommand

    object StopBuying : TraderCommand
}