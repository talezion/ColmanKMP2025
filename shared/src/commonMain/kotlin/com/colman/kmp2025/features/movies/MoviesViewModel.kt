package com.colman.kmp2025.features.movies

import co.touchlab.kermit.Logger
import com.colman.kmp2025.data.firebase.FirebaseRepository
import com.colman.kmp2025.data.movies.MoviesRepository
import com.colman.kmp2025.features.BaseViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MoviesRepository,
    private val firebaseRepository: FirebaseRepository
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
            signinAnonymously()
        }
    }

    private suspend fun signinAnonymously() {
        if (Firebase.auth.currentUser == null) {
            Firebase.auth.signInAnonymously()
            try {
                Firebase.auth.signInAnonymously()
            } catch (e: FirebaseNetworkException) {
                e.message?.let { Logger.e(it) }
            }
        }
    }

    private fun fetchMovies() {
        scope.launch {

            val movies = repository.upcomingMovies()

            if (movies == null) {
                _uiState.emit(
                    MoviesState.Error(errorMessage = "Failed to fetch upcoming movies!! ")
                )
            } else {

                delay(300)

                firebaseRepository.saveMovie(movies.items.first())

                _uiState.emit(
                    MoviesState.Loaded(movies)
                )
            }
        }

    }
}