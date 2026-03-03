package com.multiplatform.book.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual open class PlatformViewModel : ViewModel(){
    protected actual val platformViewModelScope: CoroutineScope
        get() =  viewModelScope

    protected actual open fun clear() {
        super.onCleared()
    }
}