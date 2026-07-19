package com.desager.trader.ui.state

import com.desager.trader.ui.model.LogInfo
import com.desager.trader.ui.model.PurchasingStatus

internal data class TraderUiState(
    val steamTraderPrice: String,
    val steamPrice: String,
    val fee: String,
    val difference: String,
    val count: Int,
    val payPrice: String,
    val receivePrice: String,
    val result: String,
    val depositAmount: String,
    val purchased: Int,
    val purchasingStatus: PurchasingStatus,
    val purchasingLogs: List<LogInfo>,
)