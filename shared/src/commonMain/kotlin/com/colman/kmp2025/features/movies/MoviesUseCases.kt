package com.colman.kmp2025.features.movies

import com.colman.kmp2025.domain.GetMovies
import com.colman.kmp2025.domain.GetSavedMovies
import com.colman.kmp2025.domain.SaveMovie
import com.colman.kmp2025.domain.SignInAnonymously

data class MoviesUseCases(
    val saveMovie: SaveMovie,
    val getSavedMovies: GetSavedMovies,
    val getMovies: GetMovies,
    val signInAnonymously: SignInAnonymously
)
