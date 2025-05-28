package com.colman.kmp2025.data.movies

import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.models.Movies
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.headers
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

class RemoteMoviesRepository(
    private val client: HttpClient,
    private val bearerToken: String
): MoviesRepository {

    override suspend fun upcomingMovies(): Movies? {
        return try {
            val response: HttpResponse  = client.get("https://api.themoviedb.org/3/movie/upcoming") {
                url {
                    parameters.append("language", "en-US")
                    parameters.append("page", "1")
                }

                headers {
                    accept(ContentType.Application.Json)
                    bearerAuth(bearerToken)
                }
            }

            if (!response.status.isSuccess()) {
                // Log error
                null
            }

            val text = response.bodyAsText()
            val moviesResponse: MoviesResponse = response.body()

            Movies(items = moviesResponse.results)
        } catch (e: Exception) {
            null
        }
    }
}

@Serializable
data class MoviesResponse(
    val results: List<Movie>
)