package com.colman.kmp2025.features.favourites_movies

import com.colman.kmp2025.models.Movies

public sealed class FavouritesMoviesState {
    data object Loading : FavouritesMoviesState()
    data class Loaded(
        val movies: Movies
    ) : FavouritesMoviesState()

    data class Error(
        var errorMessage: String
    ) : FavouritesMoviesState()
}