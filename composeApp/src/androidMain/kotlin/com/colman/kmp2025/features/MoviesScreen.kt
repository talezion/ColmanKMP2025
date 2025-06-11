package com.colman.kmp2025.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.colman.kmp2025.R
import com.colman.kmp2025.features.movies.MoviesState
import com.colman.kmp2025.features.movies.MoviesViewModel
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.models.Movies
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    when(uiState) {
        is MoviesState.Error -> ErrorContent(message = uiState.errorMessage)
        is MoviesState.Loaded -> MoviesGridContent(
            uiState.movies,
            onMovieClicked = onMovieClick
        )
        MoviesState.Loading -> LoadingContent()
    }
}

@Composable
fun MoviesGridContent(
    movies: Movies,
    lazyListState: LazyGridState = rememberLazyGridState(),
    spacing: Dp = 4.dp,
    onMovieClicked: (Movie) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
         columns = GridCells.Fixed(count = 3),
        state = lazyListState,
        contentPadding = PaddingValues(spacing),
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing),
    ) {
        items(movies.items) { movie ->
            MovieGridContent(
                movie = movie,
                onClick = { onMovieClicked(movie) }
            )
        }
    }
}

@Composable
fun MoviesContent(
    movies: Movies,
    lazyListState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies.items) { movie ->
            MovieContent(movie)
        }
    }
}

@Composable
fun MovieGridContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.size(250.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(contentColor = Color.LightGray),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        onClick = onClick
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.posterPath ?: ""}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            placeholder = painterResource(R.drawable.diehard),
            error = painterResource(R.drawable.diehard)
        )
    }
}

@Composable
fun MovieContent(
    movie: Movie,
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .height(160.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.diehard),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(2f / 3f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = movie.title ?: "")
                Text(text = "${movie.voteCount}")
            }
        }
    }

}

@Composable
fun ErrorContent(message: String) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = TextStyle(
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary
        )
    }
}