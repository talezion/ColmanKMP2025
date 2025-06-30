package com.colman.kmp2025.features

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface UiState

interface ViewStateHolder<S : UiState> {
    val uiState: StateFlow<S>
}

expect abstract class BaseViewModel<S: UiState>() : ViewStateHolder<S> {
    val scope: CoroutineScope
}