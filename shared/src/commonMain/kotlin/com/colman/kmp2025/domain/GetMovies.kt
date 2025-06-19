package com.colman.kmp2025.domain

import com.colman.kmp2025.data.movies.MoviesRepository

class GetMovies(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke() = moviesRepository.upcomingMovies()
}