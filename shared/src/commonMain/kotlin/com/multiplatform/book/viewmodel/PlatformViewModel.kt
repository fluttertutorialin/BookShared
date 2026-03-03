package com.multiplatform.book.viewmodel

import kotlinx.coroutines.CoroutineScope

expect open class PlatformViewModel() {
    protected val  platformViewModelScope: CoroutineScope

    protected open fun clear()
}