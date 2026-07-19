package com.desager.trader.ui.model

import androidx.annotation.ColorRes
import com.desager.common.ui.R

enum class LogType(
    @ColorRes val colorRes: Int,
) {
    SUCCESS(R.color.green_700),
    WARNING(R.color.yellow_700),
    ERROR(R.color.red_700),
}