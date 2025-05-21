package com.colman.kmp2025.di

import com.colman.kmp2025.data.MoviesRepository
import com.colman.kmp2025.data.RemoteMoviesRepository
import com.colman.kmp2025.features.movies.MoviesViewModel
import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient

object ViewModelFactory {

    private val httpClient: HttpClient = createHttpClient()
    private val bearerToken = "{set_your_token}"

    private val repository: MoviesRepository = RemoteMoviesRepository(
        client = httpClient,
        bearerToken = bearerToken
    )

    fun createViewModel(): MoviesViewModel {
        return MoviesViewModel(
            repository = repository
        )
    }
}