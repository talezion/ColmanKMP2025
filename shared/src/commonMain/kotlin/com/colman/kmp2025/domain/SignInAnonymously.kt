package com.colman.kmp2025.domain

import com.colman.kmp2025.data.firebase.FirebaseRepository

class SignInAnonymously(
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke() {
        firebaseRepository.signInAnonymously()
    }
}