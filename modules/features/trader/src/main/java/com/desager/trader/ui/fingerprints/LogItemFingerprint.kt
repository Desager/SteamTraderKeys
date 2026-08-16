package com.desager.trader.ui.fingerprints

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.desager.common.ui.adapter.BaseViewHolder
import com.desager.common.ui.adapter.Item
import com.desager.common.ui.adapter.ItemFingerprint
import com.desager.trader.R
import com.desager.trader.databinding.ItemLogBinding
import com.desager.trader.ui.model.BuyError
import com.desager.trader.ui.model.LogInfo
import com.desager.trader.ui.model.LogMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class LogItemFingerprint : ItemFingerprint<ItemLogBinding, LogInfo> {

    override fun isRelativeItem(item: Item) = item is LogInfo

    override fun getLayoutId() = R.layout.item_log

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemLogBinding, LogInfo> {
        val binding = ItemLogBinding.inflate(layoutInflater, parent, false)
        return LogItemViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<LogInfo>() {

        override fun areItemsTheSame(
            oldItem: LogInfo,
            newItem: LogInfo
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: LogInfo,
            newItem: LogInfo
        ) = oldItem == newItem
    }
}

internal class LogItemViewHolder(
    binding: ItemLogBinding,
) : BaseViewHolder<ItemLogBinding, LogInfo>(binding) {

    private val formatter = SimpleDateFormat(
        "HH:mm:ss.SSS",
        Locale.getDefault()
    )

    override fun onBind(item: LogInfo) {
        super.onBind(item)

        with(binding) {
            val context = binding.root.context

            val formattedTime = formatter.format(Date(item.timestamp))
            
            timeTextView.text = context.getString(R.string.log_time, formattedTime)
            logTextView.text = item.message.toText(context)

            logTextView.setTextColor(
                ContextCompat.getColor(context, item.type.colorRes)
            )
        }
    }

    private fun LogMessage.toText(context: Context): String {
        return when (this) {
            LogMessage.BuyingStarted ->
                context.getString(R.string.log_buying_started)

            LogMessage.BuyingStopped ->
                context.getString(R.string.log_buying_stopped)

            is LogMessage.KeyPurchased ->
                context.getString(
                    R.string.log_key_purchased,
                    spent
                )

            is LogMessage.BuyingError ->
                context.getString(
                    R.string.log_buying_error,
                    error.toText(context)
                )

            LogMessage.BuyingFinished ->
                context.getString(R.string.log_buying_finished)
        }
    }

    private fun BuyError.toText(context: Context): String {
        return when (this) {
            BuyError.BalanceNotEnough ->
                context.getString(R.string.buy_error_balance_not_enough)

            BuyError.DataPassedNotCorrect ->
                context.getString(R.string.buy_error_data_passed_not_correct)

            is BuyError.Unknown ->
                details ?: context.getString(R.string.buy_error_unknown)
        }
    }
}
