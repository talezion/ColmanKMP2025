package com.colman.kmp2025.features.movies

import com.colman.kmp2025.features.BaseViewModel
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.models.Movies
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel: BaseViewModel() {

    private val _uiState: MutableStateFlow<MoviesState> = MutableStateFlow(MoviesState.Loading)
    val uiState: StateFlow<MoviesState> get() = _uiState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        scope.launch {
            val movies = createMockMoviesData()
            delay(1500)

            _uiState.emit(
                MoviesState.Loaded(movies)
            )
        }

    }
}

private fun createMockMoviesData(): Movies {
    val moviesList = listOf(
        Movie(id = "101", title = "Die Hard", voteCount = 4),
        Movie(id = "102", title = "The Shawshank Redemption", voteCount = 9),
        Movie(id = "103", title = "Inception", voteCount = 8),
        Movie(id = "104", title = "The Dark Knight", voteCount = 10),
        Movie(id = "105", title = "Pulp Fiction", voteCount = 7),
        Movie(id = "106", title = "The Matrix", voteCount = 9),
        Movie(id = "107", title = "Forrest Gump", voteCount = 8),
        Movie(id = "108", title = "Fight Club", voteCount = 7),
        Movie(id = "109", title = "The Godfather", voteCount = 10),
        Movie(id = "110", title = "The Lord of the Rings", voteCount = 9),
        Movie(id = "111", title = "Interstellar", voteCount = 8),
        Movie(id = "112", title = "Gladiator", voteCount = 7),
        Movie(id = "113", title = "Titanic", voteCount = 6),
        Movie(id = "114", title = "Braveheart", voteCount = 7),
        Movie(id = "115", title = "The Departed", voteCount = 8),
        Movie(id = "116", title = "Avatar", voteCount = 6),
        Movie(id = "117", title = "Whiplash", voteCount = 9),
        Movie(id = "118", title = "Joker", voteCount = 8),
        Movie(id = "119", title = "No Country for Old Men", voteCount = 7),
        Movie(id = "120", title = "The Prestige", voteCount = 9)
    )

    return Movies(items = moviesList)
}