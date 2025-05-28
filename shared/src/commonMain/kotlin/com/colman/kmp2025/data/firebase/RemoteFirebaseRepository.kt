package com.colman.kmp2025.data.firebase

import com.colman.kmp2025.models.Movie
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class RemoteFirebaseRepository: FirebaseRepository {

    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    override suspend fun deleteMovie(movie: Movie) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(uid)
            .collection("movies")
            .document(movie.id.toString())
            .delete()
    }

    override suspend fun saveMovie(movie: Movie) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(uid)
            .collection("movies")
            .document(movie.id.toString())
            .set(movie)
    }

    override suspend fun getSavedMovies(): List<Movie> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        return firestore.collection("users")
            .document(uid)
            .collection("movies")
            .get()
            .documents
            .map { it.data() } // auto-parsed by Firebase Kotlin SDK
    }
}