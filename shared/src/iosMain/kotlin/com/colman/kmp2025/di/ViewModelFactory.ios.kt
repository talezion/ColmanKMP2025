package com.colman.kmp2025.di

import com.colman.kmp2025.utils.LightSensorProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual abstract class AbstractViewModelFactory {

    actual val lightSensorProvider: LightSensorProvider = LightSensorProvider()

    actual val httpClient: HttpClient = HttpClient(Darwin) {
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.DEFAULT
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }
}