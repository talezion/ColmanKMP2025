package com.colman.kmp2025.features.favourites_movies


import com.colman.kmp2025.data.Result
import com.colman.kmp2025.features.BaseViewModel
import com.colman.kmp2025.models.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouritesMoviesViewModel(
    private val useCases: FavouritesMoviesUseCases
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<FavouritesMoviesState> =
        MutableStateFlow(FavouritesMoviesState.Loading)
    val uiState: StateFlow<FavouritesMoviesState> get() = _uiState

    private fun fetchMovies() {
        scope.launch {

            val result = useCases.getSavedMovies()
            when(result) {
                is Result.Success -> {

                    _uiState.emit(FavouritesMoviesState.Loaded(result.data ?: Movies(emptyList()) ))
                }
                is Result.Failure -> {
                    _uiState.emit(FavouritesMoviesState.Error(errorMessage = result.error?.message ?: "N/A"))
                }
            }
        }
    }
}