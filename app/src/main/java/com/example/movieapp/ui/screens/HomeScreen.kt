package com.example.movieapp.ui.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.movieapp.data.MoviePopular
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel) {
    val homeUiState = homeViewModel.uiState

    Scaffold(modifier = modifier) { innerPadding ->

        when (homeUiState) {
            is HomeUiState.Success -> {
                GridMovies(
                    modifier = Modifier.padding(innerPadding),
                    listMovie = homeUiState.listMovies
                )
            }

            HomeUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            HomeUiState.Fail -> {
                Text(
                    text = "Không tải được danh sách phim",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }


}

@Composable
fun GridMovies(modifier: Modifier = Modifier, listMovie: List<MoviePopular>) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2)
    ) {
        items(listMovie) { movie ->
            ThumbnailMovieItem(thumbnail = movie.posterPath)
        }
    }

}

@Composable
fun ThumbnailMovieItem(modifier: Modifier = Modifier, thumbnail: String) {
    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        model = "${Link.IMG_BASE}$thumbnail",
        contentDescription = null
    )
}

private object Link {
    const val IMG_BASE = "https://image.tmdb.org/t/p/w500"
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MovieAppTheme {
        GridMovies(listMovie = listOf())
    }

}