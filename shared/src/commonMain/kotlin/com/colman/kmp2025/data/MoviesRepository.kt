package com.colman.kmp2025.data

import com.colman.kmp2025.models.Movies

interface MoviesRepository {
    suspend fun upcomingMovies(): Movies?
}