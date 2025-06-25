package com.colman.kmp2025.data.movies

import com.colman.kmp2025.data.Result
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.models.Movies

interface MoviesRepository {
    suspend fun upcomingMovies(): Result<Movies, TMDBError>
    suspend fun getSavedMovies(): Result<Movies, TMDBError>
    suspend fun save(movie: Movie)
}