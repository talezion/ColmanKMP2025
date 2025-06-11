package com.colman.kmp2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.colman.kmp2025.features.MoviesScreen
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.shared_components.BottomNavigationBar
import com.google.firebase.FirebaseApp

sealed class MovieTab(val route: String, val title: String) {
    data object Home : MovieTab("home", "Home")
    data object Favorites : MovieTab("favorites", "Favorites")
}

class MainActivity : ComponentActivity() {

//    private val moviesViewModel: MoviesViewModel by viewModels { MoviesViewModelFactory() }
//    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        initViewModelFactory(applicationContext)

        FirebaseApp.initializeApp(this)

        setContent {

            MaterialTheme {

                val navController = rememberNavController()
                var selectedTab by remember { mutableStateOf<MovieTab>(MovieTab.Home) }

                Scaffold(
                    topBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val movieId = navBackStackEntry?.arguments?.getString("movieId")
                        val currentRoute = navBackStackEntry?.destination?.route
                        val isMovieScreen = currentRoute == "movie"
                        val appBarTitle: String = when {
                            currentRoute?.startsWith("movie/") == true -> movieId ?: "Movie"
                            else -> "Movies"
                        }

                        AppBar(
                            title = appBarTitle,
                            showBackButton = isMovieScreen,
                            onBackClick = { navController.popBackStack() }
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            selectedTab = selectedTab,
                            onTabSelected = {
                                selectedTab = it
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = MovieTab.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(MovieTab.Home.route) {
                            MoviesScreen(
                                onMovieClick = { movie ->
                                    navController.navigateToMovie(movie)
                                }
                            )
                        }

                        composable(MovieTab.Favorites.route) {
                            MoviesScreen(
                                onMovieClick = { movie ->
                                    navController.navigateToMovie(movie)
                                }
                            )
                        }

                        composable("movie/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            if (movieId != null) {
                                // TODO For next class: inject view model
                                MovieDetailsScreen(movieId)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsScreen(movieId: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    )  {
        Text("Hi ${movieId}")
    }
}

fun NavController.navigateToMovie(movie: Movie) {
//    this.currentBackStackEntry?.savedStateHandle?.set("movie", movie)
//    this.navigate("movie")
    this.navigate("movie/${movie.id}")
}



// Fix scrollable top app bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        windowInsets = TopAppBarDefaults.windowInsets,
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}