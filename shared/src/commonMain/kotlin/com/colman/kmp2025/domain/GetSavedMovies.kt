package com.colman.kmp2025.domain

import com.colman.kmp2025.data.movies.MoviesRepository

class GetSavedMovies(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke() = moviesRepository.getSavedMovies()
}