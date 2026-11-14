package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.ui.screens.YoutubePlayerScreen
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.ui.screens.HomeViewModel
import com.example.movieapp.ui.screens.MovieDetailScreen
import com.example.movieapp.ui.screens.MovieDetailViewModel
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                val movieDetailViewModel: MovieDetailViewModel =
                    viewModel(factory = MovieDetailViewModel.Factory)

                val navHostController = rememberNavController()
                NavHost(
                    navController = navHostController,
                    startDestination = AppScreen.Home.name
                ) {
                    composable(AppScreen.Home.name) {
                        HomeScreen(
                            popularMoviesUiState = homeViewModel.PopularMoviesUiState,
                            searchMovieUiState = homeViewModel.searchMovieUiState,
                            onSelect = { movieId, movieName ->
                                movieDetailViewModel.getMovieDetail(movieId)
                                navHostController.navigate("${AppScreen.Detail.name}/$movieName")
                            },
                            onSearching = homeViewModel::getSearchResultMovies
                        )
                    }
                    composable(
                        "${AppScreen.Detail.name}/{title}",
                        arguments = listOf(
                            navArgument("title") {
                                type = NavType.StringType
                            },
                        )
                    ) { backStackEntry ->
                        val canNavigateBack = navHostController.previousBackStackEntry != null
                        val title = backStackEntry.arguments?.getString("title").orEmpty()
                        MovieDetailScreen(
                            title = title,
                            movieDetailUiState = movieDetailViewModel.uiState,
                            onPlay = { videoId ->
                                navHostController.navigate("${AppScreen.YoutubePlayer.name}/$videoId")
                            },
                            canNavigateBack = canNavigateBack,
                            onNavigateBack = { navHostController.popBackStack() }
                        )
                    }
                    composable(
                        route = "${AppScreen.YoutubePlayer.name}/{videoId}",
                        arguments = listOf(
                            navArgument("videoId") {
                                type = NavType.StringType
                            },
                        )
                    ) { backStackEntry ->
                        val videoId = backStackEntry.arguments?.getString("videoId").orEmpty()
                        YoutubePlayerScreen(videoId = videoId, onBack = {
                            navHostController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}

enum class AppScreen {
    Home, Detail, YoutubePlayer
}