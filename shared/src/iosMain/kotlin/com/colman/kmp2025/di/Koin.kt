package com.colman.kmp2025.di

import com.colman.kmp2025.features.favourites_movies.FavouritesMoviesViewModel
import com.colman.kmp2025.features.movie.MovieViewModel
import com.colman.kmp2025.features.movies.MoviesViewModel
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatform

lateinit var sharedKoin: Koin

fun doInitKoin() {
    initKoin()
    sharedKoin = KoinPlatform.getKoin()
}

fun moviesViewModel(): MoviesViewModel = KoinPlatform.getKoin().get()
fun movieViewModel(): MovieViewModel = KoinPlatform.getKoin().get()
fun favouritesMoviesViewModel(): FavouritesMoviesViewModel = KoinPlatform.getKoin().get()

@OptIn(BetaInteropApi::class)
fun Koin.get(objCClass: ObjCClass): Any? {
    val kClazz = getOriginalKotlinClass(objCClass) ?: return null
    return get(kClazz, null, null)
}

@OptIn(BetaInteropApi::class)
fun Koin.get(
    objCClass: ObjCClass,
    qualifier: Qualifier?,
    parameter: Any
): Any? {
    val kClazz = getOriginalKotlinClass(objCClass) ?: return null
    return get(kClazz, qualifier) { parametersOf(parameter) }
}