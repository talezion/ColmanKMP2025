package com.colman.kmp2025.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colman.kmp2025.features.movie.MovieViewModel
import com.colman.kmp2025.features.movies.MoviesViewModel
import com.colman.kmp2025.utils.LightSensorProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private lateinit var appContext: Context

fun initViewModelFactory(context: Context) {
    appContext = context.applicationContext
}

actual abstract class AbstractViewModelFactory {

    actual val lightSensorProvider: LightSensorProvider = LightSensorProvider(
        appContext
    )

    actual val httpClient: HttpClient = HttpClient(OkHttp) {
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.DEFAULT
        }
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
            return ViewModelFactory.createMoviesViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MovieViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelFactory.createMovieViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
