package com.multiplatform.book.viewmodel

import com.multiplatform.book.createBookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*actual open class PlatformViewModel {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    protected actual val platformViewModelScope: CoroutineScope
        get() = scope

    protected actual open fun clear() {
        platformViewModelScope.cancel()
    }

    fun observeFLow(flow: StateFlow<*>, onChange: (Any?) -> Unit) {
        platformViewModelScope.launch {
            flow.collect { value ->
                onChange(value)
            }
        }
    }
}*/


actual open class PlatformViewModel {

    private val scope: CoroutineScope by lazy(::buildScope)

    protected actual val platformViewModelScope: CoroutineScope
        get() = scope

    private fun buildScope(): CloseableCoroutineScope = CloseableCoroutineScope(
            SupervisorJob() + Dispatchers.Main.immediate
        ).apply {
            close()
    }

    protected actual open fun clear() {
        scope.cancel()
    }

    fun observeFLow(flow: StateFlow<*>, onChange: (Any?) -> Unit) {
        platformViewModelScope.launch {
            flow.collect { value ->
                onChange(value)
            }
        }
    }
}

object ViewModelFactory {
    fun booksViewModel(): BooksViewModel {
        return BooksViewModel(createBookRepository())
    }
}

class CloseableCoroutineScope(
    context: CoroutineContext
) : CoroutineScope {

    private val job = Job(context[Job])
    override val coroutineContext: CoroutineContext = context + job

    fun close() {
        coroutineContext.cancel()
    }
}


