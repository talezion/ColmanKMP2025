package com.colman.kmp2025.data.movies

import com.colman.kmp2025.data.Error
import com.colman.kmp2025.data.Result
import com.colman.kmp2025.data.dao.MovieDao
import com.colman.kmp2025.data.networking.Api
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

data class TMDBError (
    override val message: String
) : Error

val bearerToken = "YOUR_BEARER_TOKEN_HERE"

// Refactor client to be generic and injectable
class RemoteMoviesRepository(
    private val client: HttpClient,
    private val movieDao: MovieDao,
//    private val api: Api<MoviesEndPoint>
): MoviesRepository {

    override suspend fun save(movie: Movie) {
        movieDao.insertMovie(movie)
    }




//    override suspend fun getPopularMovies(page: String): Result<Movies, TMDBError>  {
//        return try {
//            api.request<Movies, TMDBError>(MoviesEndPoint.GetPopularMovies(page = page))
//        } catch (e: Exception) {
//            Result.Failure(
//                TMDBError(message = e.message ?: "Failed to fetch saved movies")
//            )
//        }
//    }




    override suspend fun getSavedMovies(): Result<Movies, TMDBError> {
        return try {
            Result.Success(Movies(items = movieDao.getAllMovies()))
        } catch (e: Exception) {
            Result.Failure(
                TMDBError(message = e.message ?: "Failed to fetch saved movies from local db")
            )
        }
    }

    override suspend fun upcomingMovies(): Result<Movies, TMDBError> {
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
                Result.Failure(
                    TMDBError(message = "Failed to fetch upcoming movies: ${response.status.value}")
                )
            }

            val text = response.bodyAsText()
            val moviesResponse: MoviesResponse = response.body()

            Result.Success(Movies(items = moviesResponse.results))
        } catch (e: Exception) {
            Result.Failure(
                TMDBError(message = e.message ?: "")
            )
        }
    }
}

@Serializable
data class MoviesResponse(
    val results: List<Movie>
)