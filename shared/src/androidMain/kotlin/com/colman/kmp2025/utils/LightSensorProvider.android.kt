package com.colman.kmp2025.utils

import android.content.Context

actual class LightSensorProvider(
    private val context: Context
) {

    actual suspend fun readAmbientLight(): Float {
        return  0.2f // Placeholder value for Android, as actual ambient light reading requires sensor access
    }
}