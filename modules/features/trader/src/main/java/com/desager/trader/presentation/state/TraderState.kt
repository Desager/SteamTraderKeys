package com.desager.trader.presentation.state

import com.desager.trader.ui.model.LogInfo
import com.desager.trader.ui.model.PurchasingStatus

internal data class TraderState(
    val steamTraderPrice: Double = 0.0,
    val steamPrice: Double = 0.0,
    val fee: Double = 0.0,
    val difference: Double = 0.0,
    val count: Int = 1,
    val payPrice: Double = 0.0,
    val receivePrice: Double = 0.0,
    val result: Double = 0.0,
    val depositAmount: Double = 0.0,
    val purchased: Int = 0,
    val purchasingStatus: PurchasingStatus = PurchasingStatus.INACTIVE,
    val purchasingLogs: List<LogInfo> = emptyList(),
)