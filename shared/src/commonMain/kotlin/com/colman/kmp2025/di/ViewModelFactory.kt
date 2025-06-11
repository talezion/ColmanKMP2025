package com.colman.kmp2025.di

import com.colman.kmp2025.data.firebase.FirebaseRepository
import com.colman.kmp2025.data.firebase.RemoteFirebaseRepository
import com.colman.kmp2025.data.movies.MoviesRepository
import com.colman.kmp2025.data.movies.RemoteMoviesRepository
import com.colman.kmp2025.features.movie.MovieViewModel
import com.colman.kmp2025.features.movies.MoviesViewModel
import com.colman.kmp2025.utils.LightSensorProvider
import io.ktor.client.HttpClient

//expect abstract class AbstractViewModelFactory() {
//    val httpClient: HttpClient
//    val lightSensorProvider: LightSensorProvider
//}

//object ViewModelFactory: AbstractViewModelFactory() {

//

//    private val repository: MoviesRepository = RemoteMoviesRepository(
//        client = httpClient,
//        bearerToken = bearerToken
//    )

//    private val firebaseRepository: FirebaseRepository = RemoteFirebaseRepository()
//
//    fun createMoviesViewModel(): MoviesViewModel {
//        return MoviesViewModel(
//            repository = repository,
//            firebaseRepository = firebaseRepository
//        )
//    }
//
//    fun createMovieViewModel(): MovieViewModel {
//        return MovieViewModel(
//            repository = repository,
//            firebaseRepository = firebaseRepository,
//            lightProvider = lightSensorProvider
//        )
//    }
//}