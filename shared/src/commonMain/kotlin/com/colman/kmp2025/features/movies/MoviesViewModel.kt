package com.colman.kmp2025.features.movies

import co.touchlab.kermit.Logger
import com.colman.kmp2025.data.firebase.FirebaseRepository
import com.colman.kmp2025.data.movies.MoviesRepository
import com.colman.kmp2025.features.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.colman.kmp2025.data.Result
import com.colman.kmp2025.domain.GetMovies
import com.colman.kmp2025.domain.SignInAnonymously
import com.colman.kmp2025.models.Movies

sealed class MoviesTab {
    class Upcoming(val title: String) : MoviesTab()
    data object Popular : MoviesTab()
    data object TopRated : MoviesTab()
    data object Favourites : MoviesTab()
}

class MoviesViewModel(
    val useCases: MoviesUseCases
): BaseViewModel() {

    private val _uiState: MutableStateFlow<MoviesState> = MutableStateFlow(MoviesState.Loading)
    val uiState: StateFlow<MoviesState> get() = _uiState

    private var log = Logger.withTag("MoviesViewModel")
    private var logflow = Logger.withTag("MoviesViewModel-FLOW")

    init {
        fetchMovies()
        signin()
    }

    private fun signin() {
        scope.launch {
            useCases.signInAnonymously()
        }
    }

    private fun fetchMovies() {
        scope.launch {

//            val result = useCases.getSavedMovies()
            val result = useCases.getMovies()
            when(result) {
                is Result.Success -> {

                    result.data?.items?.firstOrNull()?.let { movie ->
                        useCases.saveMovie(movie)
                    }

                    _uiState.emit(MoviesState.Loaded(result.data ?: Movies(emptyList()) ))
                }
                is Result.Failure -> {
                    _uiState.emit(MoviesState.Error(errorMessage = result.error?.message ?: "N/A"))
                }
            }
        }
    }
}