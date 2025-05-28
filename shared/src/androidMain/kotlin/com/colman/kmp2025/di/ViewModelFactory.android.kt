package com.colman.kmp2025.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colman.kmp2025.features.movies.MoviesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual abstract class AbstractViewModelFactory {
    actual val httpClient: HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }
}

class MoviesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelFactory.createViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
