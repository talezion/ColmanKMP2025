package com.colman.kmp2025.data.movies

import com.colman.kmp2025.data.networking.Endpoint
import com.colman.kmp2025.data.networking.QueryParam
import com.colman.kmp2025.models.Movie
import io.ktor.http.HttpMethod

// https://api.themoviedb.org/3/

sealed interface MoviesEndPoint: Endpoint {

    data class GetUpcomingMovies(
        val page: String
    ) : MoviesEndPoint
    data class GetTopRatedMovies(
        val page: String
    )  : MoviesEndPoint
    data class GetPopularMovies(
        val page: String
    )  : MoviesEndPoint
    data class GetNowPlayingMovies(
        val page: String
    )  : MoviesEndPoint

    data class GetMovieRecommendations(
        val movie: Movie,
        val page: String
    ) : MoviesEndPoint

    data class GetSimilatrMovies(
        val movie: Movie,
        val page: String
    ) : MoviesEndPoint

    override val path: String
        get() = when (this) {
            is GetUpcomingMovies -> "movie/upcoming"
            is GetTopRatedMovies -> "movie/top_rated"
            is GetPopularMovies -> "movie/popular"
            is GetNowPlayingMovies -> "movie/now_playing"
            is GetMovieRecommendations -> "movie/${movie.id}/recommendations"
            is GetSimilatrMovies -> "movie/${movie.id}/similar"
        }

    override val method: HttpMethod
        get() = when (this) {
            is GetUpcomingMovies,
            is GetNowPlayingMovies,
            is GetPopularMovies,
            is GetTopRatedMovies,
            is GetMovieRecommendations,
            is GetSimilatrMovies -> HttpMethod.Get
        }

    override val queryParameters: List<QueryParam>?
        get() = when (this) {
            is GetUpcomingMovies  -> listOf(
                QueryParam("language", "en-US"),
                QueryParam("page", page)
            )
            is GetTopRatedMovies -> listOf(
                QueryParam("language", "en-US"),
                QueryParam("page", page)
            )
            is GetPopularMovies -> listOf(
                QueryParam("language", "en-US"),
                QueryParam("page", page)
            )
            is GetNowPlayingMovies -> listOf(
                QueryParam("language", "en-US"),
                QueryParam("page", page)
            )
            is GetMovieRecommendations -> listOf(
                QueryParam("language", "en-US"),
                QueryParam("page", page)
            )
            is GetSimilatrMovies -> listOf(
                QueryParam("language", "en-US"),
                QueryParam("page", page)
            )
        }
}