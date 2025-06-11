package com.colman.kmp2025.di

import com.colman.kmp2025.features.movie.MovieViewModel
import com.colman.kmp2025.features.movies.MoviesViewModel
import com.colman.kmp2025.utils.LightSensorProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    singleOf(::LightSensorProvider)

    factoryOf(::MoviesViewModel)
    factoryOf(::MovieViewModel)
}