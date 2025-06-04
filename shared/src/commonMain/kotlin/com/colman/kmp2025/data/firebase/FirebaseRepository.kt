package com.colman.kmp2025.data.firebase

import com.colman.kmp2025.models.Movie

interface FirebaseRepository {
    suspend fun saveMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
    suspend fun getSavedMovies(): List<Movie>
    suspend fun signInAnonymously()
}