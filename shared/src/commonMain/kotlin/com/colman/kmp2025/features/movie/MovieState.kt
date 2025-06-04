package com.colman.kmp2025.features.movie

import com.colman.kmp2025.models.Movie

public sealed class MovieState {
    data object Loading : MovieState()
    data class Loaded(
        val data: Movie
    ) : MovieState()

    data class Error(
        var errorMessage: String
    ) : MovieState()
}