package com.desager.arch.android.storeholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desager.arch.mvi.MviStore
import kotlin.reflect.KProperty

class StoreHolder<out S : MviStore<*, *, *>>(
    val store: S
) : ViewModel() {

    init {
        store.attach(viewModelScope)
    }
}

operator fun <S : MviStore<*, *, *>> Lazy<StoreHolder<S>>.getValue(thisObj: Any?, property: KProperty<*>): S = value.store