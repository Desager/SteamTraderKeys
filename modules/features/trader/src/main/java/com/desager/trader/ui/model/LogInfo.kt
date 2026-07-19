package com.desager.trader.ui.model

import com.desager.common.ui.adapter.Item
import java.util.UUID

data class LogInfo(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val message: LogMessage,
    val type: LogType,
) : Item {

    companion object {
        fun success(message: LogMessage) = LogInfo(
            message = message,
            type = LogType.SUCCESS,
        )

        fun warning(message: LogMessage) = LogInfo(
            message = message,
            type = LogType.WARNING,
        )

        fun error(message: LogMessage) = LogInfo(
            message = message,
            type = LogType.ERROR,
        )
    }
}