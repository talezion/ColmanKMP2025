package com.colman.kmp2025.utils

import platform.UIKit.UIScreen

actual class LightSensorProvider {
    actual suspend fun readAmbientLight(): Float {
        return UIScreen.mainScreen.brightness().toFloat()
    }
}