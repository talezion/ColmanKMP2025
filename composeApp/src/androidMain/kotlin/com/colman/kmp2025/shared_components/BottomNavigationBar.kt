package com.colman.kmp2025.shared_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.colman.kmp2025.MovieTab

@Composable
fun BottomNavigationBar(
    selectedTab: MovieTab,
    onTabSelected: (MovieTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab is MovieTab.Home,
            onClick = { onTabSelected(MovieTab.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = selectedTab is MovieTab.Favorites,
            onClick = { onTabSelected(MovieTab.Favorites) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") }
        )
    }
}