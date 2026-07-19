package com.desager.common.ui.adapter

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<out V : ViewBinding, I : Item>(
    protected val binding: V,
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    @CallSuper
    open fun onBind(item: I) {
        this.item = item
    }

    @CallSuper
    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }
}