package com.desager.arch.android.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.desager.arch.android.storeholder.StoreHolder
import com.desager.arch.android.storeholder.StoreHolderFactory
import com.desager.arch.mvi.MviStore

inline fun <reified S : MviStore<*, *, *>> Fragment.storeHolder(
    crossinline factory: () -> S
): Lazy<StoreHolder<S>> {
    return viewModels {
        StoreHolderFactory {
            StoreHolder(factory())
        }
    }
}