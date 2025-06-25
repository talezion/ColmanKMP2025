package com.colman.kmp2025.features.favourites_movies

import com.colman.kmp2025.domain.GetSavedMovies
import com.colman.kmp2025.domain.SaveMovie

data class FavouritesMoviesUseCases(
    val saveMovie: SaveMovie,
    val getSavedMovies: GetSavedMovies,
)