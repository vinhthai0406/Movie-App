package com.example.movieapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.ui.components.YoutubePlayerScreen
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.ui.screens.HomeViewModel
import com.example.movieapp.ui.screens.MovieDetailScreen
import com.example.movieapp.ui.screens.MovieDetailViewModel

@Composable
fun MovieApp(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val movieDetailViewModel: MovieDetailViewModel =
        viewModel(factory = MovieDetailViewModel.Factory)
    val navHostController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = MovieApp.Starter.name
        ) {
            composable(MovieApp.Starter.name) {
                HomeScreen(
                    modifier = modifier.padding(innerPadding),
                    homeViewModel = homeViewModel,
                    onSelect = { movieId ->
                        movieDetailViewModel.getMovieDetail(movieId)
                        navHostController.navigate(MovieApp.Detail.name)
                    }
                )
            }
            composable(MovieApp.Detail.name) {
                MovieDetailScreen(
                    modifier = modifier.padding(innerPadding),
                    viewModel = movieDetailViewModel,
                    onPlay = { videoId ->
                        navHostController.navigate("${MovieApp.YoutubePlayer.name}/$videoId")
                    }
                )
            }
            composable(
                route = "${MovieApp.YoutubePlayer.name}/{videoId}", arguments = listOf(
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

enum class MovieApp {
    Starter, Detail, YoutubePlayer
}