package com.colman.kmp2025.di

import com.colman.kmp2025.data.firebase.FirebaseRepository
import com.colman.kmp2025.data.firebase.RemoteFirebaseRepository
import com.colman.kmp2025.data.movies.MoviesRepository
import com.colman.kmp2025.data.movies.RemoteMoviesRepository
import com.colman.kmp2025.domain.GetMovies
import com.colman.kmp2025.domain.SignInAnonymously
import com.colman.kmp2025.features.movie.MovieViewModel
import com.colman.kmp2025.features.movies.MoviesUseCases
import com.colman.kmp2025.features.movies.MoviesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModules())
    }
}

fun initKoin() = initKoin { }

fun appModules() = listOf(commonModule, platformModule, domainModule)

expect val platformModule: Module

val domainModule = module {
    factoryOf(::SignInAnonymously)
    factoryOf(::GetMovies)
    factoryOf(::MoviesUseCases)
}

val commonModule = module {
    singleOf(::createJson)
    singleOf(::RemoteFirebaseRepository).bind<FirebaseRepository>()
    singleOf(::RemoteMoviesRepository).bind<MoviesRepository>()

    single { createHttpClient(get(), get()) }
}

fun createJson(): Json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
}

fun createHttpClient(clientEngine: HttpClientEngine, json: Json) = HttpClient(clientEngine) {
    install(Logging) {
        level = LogLevel.ALL
        logger = Logger.DEFAULT
    }
    install(ContentNegotiation) {
        json(json)
    }
}