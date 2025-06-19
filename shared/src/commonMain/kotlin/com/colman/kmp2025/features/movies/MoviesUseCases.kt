package com.colman.kmp2025.features.movies

import com.colman.kmp2025.domain.GetMovies
import com.colman.kmp2025.domain.SignInAnonymously

data class MoviesUseCases(
    val getMovies: GetMovies,
    val signInAnonymously: SignInAnonymously
)
