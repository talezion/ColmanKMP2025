package com.colman.kmp2025.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.colman.kmp2025.features.favourites_movies.FavouritesMoviesState
import com.colman.kmp2025.features.favourites_movies.FavouritesMoviesViewModel
import com.colman.kmp2025.features.movies.MoviesState
import com.colman.kmp2025.models.Movie
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouritesMoviesScreen(
    viewModel: FavouritesMoviesViewModel = koinViewModel(),
    onMovieClick: (Movie) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState().value

    when(uiState) {
        is FavouritesMoviesState.Error -> ErrorContent(message = uiState.errorMessage)
        is FavouritesMoviesState.Loaded -> MoviesGridContent(
            uiState.movies,
            onMovieClicked = onMovieClick
        )
        FavouritesMoviesState.Loading -> LoadingContent()
    }
}