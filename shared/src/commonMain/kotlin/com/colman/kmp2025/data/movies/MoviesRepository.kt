package com.colman.kmp2025.data.movies

import com.colman.kmp2025.models.Movies

interface MoviesRepository {
    suspend fun upcomingMovies(): Movies?
}