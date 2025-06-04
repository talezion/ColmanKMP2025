package com.colman.kmp2025.features.movie


import com.colman.kmp2025.data.firebase.FirebaseRepository
import com.colman.kmp2025.data.movies.MoviesRepository
import com.colman.kmp2025.features.BaseViewModel
import com.colman.kmp2025.utils.LightSensorProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieViewModel(
    private val repository: MoviesRepository,
    private val firebaseRepository: FirebaseRepository,
    private val lightProvider: LightSensorProvider
): BaseViewModel() {

    private val _uiState: MutableStateFlow<MovieState> = MutableStateFlow(MovieState.Loading)
    val uiState: StateFlow<MovieState> get() = _uiState

    private val _lux = MutableStateFlow<Float?>(null)
    val lux: StateFlow<Float?> get() = _lux

    fun loadAmbientLight(): Float? {
        return null
    }
}