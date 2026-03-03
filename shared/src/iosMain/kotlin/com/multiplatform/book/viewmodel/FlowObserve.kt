package com.multiplatform.book.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> observeFlow(
    flow: StateFlow<T>,
    onChange: (T) -> Unit
): FlowCancellable {

    val job = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    ).launch {
        flow.collect { value ->
            onChange(value)
        }
    }

    return FlowCancellable(job)
}

class FlowCancellable(
    private val job: Job
) {
    fun cancel() {
        job.cancel()
    }
}