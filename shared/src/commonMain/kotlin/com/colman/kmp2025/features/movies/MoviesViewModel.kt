package com.colman.kmp2025.features.movies

import co.touchlab.kermit.Logger
import com.colman.kmp2025.data.MoviesRepository
import com.colman.kmp2025.features.BaseViewModel
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.models.Movies
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MoviesRepository
): BaseViewModel() {

    private val _uiState: MutableStateFlow<MoviesState> = MutableStateFlow(MoviesState.Loading)
    val uiState: StateFlow<MoviesState> get() = _uiState

    private var log = Logger.withTag("MoviesViewModel")
    private var logflow = Logger.withTag("MoviesViewModel-FLOW")

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        scope.launch {

            val movies = repository.upcomingMovies()

            if (movies == null) {
                _uiState.emit(
                    MoviesState.Error(errorMessage = "Failed to fetch upcoming movies!! ")
                )
            } else {
                _uiState.emit(
                    MoviesState.Loaded(movies)
                )
            }
        }

    }
}