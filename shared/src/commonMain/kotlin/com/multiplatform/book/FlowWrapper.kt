package com.multiplatform.book

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow

/**
 * A wrapper that lets Swift code observe a Kotlin StateFlow.
 *
 * Usage from Swift:
 * ```swift
 * let wrapper = FlowWrapper<WalletState>(flow: viewModel.state)
 * wrapper.subscribe { state in
 *     self.walletState = state as! WalletState
 * }
 * ```
 *
 * Call [unsubscribe] when the observer is no longer needed.
 */
class FlowWrapper<T : Any>(private val flow: StateFlow<T>) {
    private var job: Job? = null

    /**
     * The current snapshot value (non-reactive).
     */
    val value: T get() = flow.value

    /**
     * Subscribe to all emissions on the main thread.
     * The callback is invoked immediately with the current value, then on each change.
     */
    fun subscribe(onEach: (T) -> Unit) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            flow.collect { onEach(it) }
        }
    }

    /**
     * Cancel the active subscription.
     */
    fun unsubscribe() {
        job?.cancel()
        job = null
    }
}

/*
class WalletViewModel(
    private val portfolioRepository: PortfolioRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val _state = MutableStateFlow(PortfolioState())
    val state: StateFlow<PortfolioState> = _state

    private val scope = CoroutineScope(dispatcher)

    fun loadWallet() {
    }
}

class WalletViewModelWrapper: ObservableObject {
    let viewModel: WalletViewModel
    @Published var state: PortfolioState
    private var flowWrapper: FlowWrapper<PortfolioState>?

    init(viewModel: WalletViewModel) {
        self.viewModel = viewModel
        self.state = viewModel.state.value as! PortfolioState

        let wrapper = FlowWrapper<PortfolioState>(flow: viewModel.state)
        self.flowWrapper = wrapper
        wrapper.subscribe { [weak self] newState in
            DispatchQueue.main.async {
                self?.state = newState as! PortfolioState
            }
        }
    }

    func loadWallet() { viewModel.loadWallet() }

    func refresh() { viewModel.refresh() }

    deinit { flowWrapper?.unsubscribe() }
}
*/