package com.colman.kmp2025.domain

import com.colman.kmp2025.data.firebase.FirebaseRepository
import com.colman.kmp2025.data.movies.MoviesRepository
import com.colman.kmp2025.models.Movie

class SaveMovie(
    private val moviesRepository: MoviesRepository,
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(movie: Movie) {
        moviesRepository.save(movie)
        firebaseRepository.saveMovie(movie)
    }
}