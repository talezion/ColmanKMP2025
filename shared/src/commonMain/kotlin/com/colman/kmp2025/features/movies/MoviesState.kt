package com.colman.kmp2025.features.movies

import com.colman.kmp2025.models.Movies

public sealed class MoviesState {
    data object Loading: MoviesState()
    data class Loaded(
        val movies: Movies
    ): MoviesState()
    data class Error(
        var errorMessage: String
    ): MoviesState()
}

// Add uiEvent sealed class