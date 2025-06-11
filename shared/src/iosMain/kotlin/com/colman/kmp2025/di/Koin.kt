package com.colman.kmp2025.di

import com.colman.kmp2025.features.movie.MovieViewModel
import com.colman.kmp2025.features.movies.MoviesViewModel
import org.koin.mp.KoinPlatform

fun doInitKoin() = initKoin()

fun moviesViewModel(): MoviesViewModel = KoinPlatform.getKoin().get()
fun movieViewModel(): MovieViewModel = KoinPlatform.getKoin().get()

