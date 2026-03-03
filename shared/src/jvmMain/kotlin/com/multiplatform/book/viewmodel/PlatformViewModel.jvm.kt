package com.multiplatform.book.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual open class PlatformViewModel {
    private  val  scope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    protected actual val platformViewModelScope: CoroutineScope
        get() =  scope

    protected actual open fun clear() {
        platformViewModelScope.cancel()
    }
}