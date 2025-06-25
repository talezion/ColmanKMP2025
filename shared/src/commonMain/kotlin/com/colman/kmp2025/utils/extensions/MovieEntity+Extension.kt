package com.colman.kmp2025.utils.extensions

import com.colman.kmp2025.MovieEntity
import com.colman.kmp2025.models.Movie

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id.toInt(),
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        popularity = popularity,
        voteCount = voteCount?.toInt()
    )
}