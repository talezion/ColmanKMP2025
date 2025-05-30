package com.colman.kmp2025.di

import com.colman.kmp2025.data.firebase.FirebaseRepository
import com.colman.kmp2025.data.firebase.RemoteFirebaseRepository
import com.colman.kmp2025.data.movies.MoviesRepository
import com.colman.kmp2025.data.movies.RemoteMoviesRepository
import com.colman.kmp2025.features.movies.MoviesViewModel
import io.ktor.client.HttpClient

expect abstract class AbstractViewModelFactory() {
    val httpClient: HttpClient
}

object ViewModelFactory: AbstractViewModelFactory() {

    private val bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1YzFkZTRjNTYwOWZjMjFiMGIxNDUyZTQyYTcyZmJlYiIsIm5iZiI6MTU3MzY1NTM1Ny45MjYsInN1YiI6IjVkY2MxMzNkMWQ3OGYyMDAxODI0NzIyOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JBCRjw5RKlVIiDOpfPHDVQ7wXYHES_scI9aV0Ms4kpM"

    private val repository: MoviesRepository = RemoteMoviesRepository(
        client = httpClient,
        bearerToken = bearerToken
    )

    private val firebaseRepository: FirebaseRepository = RemoteFirebaseRepository()

    fun createViewModel(): MoviesViewModel {
        return MoviesViewModel(
            repository = repository,
            firebaseRepository = firebaseRepository
        )
    }
}