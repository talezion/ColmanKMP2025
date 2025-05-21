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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colman.kmp2025.R
import com.colman.kmp2025.features.movies.MoviesState
import com.colman.kmp2025.features.movies.MoviesViewModel
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.models.Movies

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value
    when(uiState) {
        is MoviesState.Error -> ErrorContent(message = uiState.errorMessage)
        is MoviesState.Loaded -> MoviesContent(uiState.movies)
        MoviesState.Loading -> LoadingContent()
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