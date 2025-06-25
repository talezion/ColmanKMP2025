package com.colman.kmp2025.data.dao

import com.colman.kmp2025.MoviesQueries
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.utils.extensions.toMovie

class MovieDao(
    private val quiries: MoviesQueries
) {

    fun insertMovie(movie: Movie) {
        quiries.insertMovie(
            id = movie.id?.toLong(),
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            backdropPath = movie.backdropPath,
            originalTitle = movie.originalTitle,
            popularity = movie.popularity,
            voteCount = movie.voteCount?.toLong()
        )
    }

    fun getMovieById(id: Long): Movie? {
        return quiries.selectMovieById(id).executeAsOneOrNull()?.toMovie()
    }

    fun getAllMovies(): List<Movie> {
        return quiries.selectAllMovies().executeAsList().map { it.toMovie() }
    }
}