package com.colman.kmp2025.di

import com.colman.kmp2025.features.movies.MoviesViewModel
import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient

object ViewModelFactory {

    private val httpClient: HttpClient = createHttpClient()
    private val bearerToken = ""

    // TODO in next lesson - Create an instance of MoviesRepository

    fun createViewModel(): MoviesViewModel {
        return MoviesViewModel() // Inject repo
    }
}