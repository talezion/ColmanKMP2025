package com.colman.kmp2025.utils

expect class LightSensorProvider {
    suspend fun readAmbientLight(): Float
}
