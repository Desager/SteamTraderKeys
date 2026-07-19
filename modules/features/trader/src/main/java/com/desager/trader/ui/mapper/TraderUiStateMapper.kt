package com.desager.trader.ui.mapper

import com.desager.arch.tea.mapper.UiStateMapper
import java.text.NumberFormat
import com.desager.trader.presentation.state.TraderState as State
import com.desager.trader.ui.state.TraderUiState as UiState

internal class TraderUiStateMapper : UiStateMapper<State, UiState> {

    private val numberFormat = NumberFormat.getNumberInstance().apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    override fun map(state: State): UiState {
        return UiState(
            steamTraderPrice = state.steamTraderPrice.format(),
            steamPrice = state.steamPrice.format(),
            fee = state.fee.format(),
            difference = state.difference.format(),
            count = state.count,
            payPrice = state.payPrice.format(),
            receivePrice = state.receivePrice.format(),
            result = state.result.formatWithSign(),
            depositAmount = state.depositAmount.format(),
            purchased = state.purchased,
            purchasingStatus = state.purchasingStatus,
            purchasingLogs = state.purchasingLogs,
        )
    }

    private fun Double.format(): String {
        return numberFormat.format(this)
    }

    private fun Double.formatWithSign(): String {
        val sign = if (this > 0) "+" else ""
        return sign + numberFormat.format(this)
    }
}