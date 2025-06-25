package com.colman.kmp2025.di

import app.cash.sqldelight.db.SqlDriver
import com.colman.kmp2025.data.dao.DatabaseDriverFactory
import com.colman.kmp2025.features.favourites_movies.FavouritesMoviesViewModel
import com.colman.kmp2025.features.movie.MovieViewModel
import com.colman.kmp2025.features.movies.MoviesViewModel
import com.colman.kmp2025.utils.LightSensorProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<LightSensorProvider> { LightSensorProvider(get()) }

    viewModelOf(::MoviesViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::FavouritesMoviesViewModel)

    single<SqlDriver> { DatabaseDriverFactory(get()).createDriver() }
}