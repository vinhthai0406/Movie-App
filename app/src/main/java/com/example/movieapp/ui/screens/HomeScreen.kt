package com.example.movieapp.ui.screens

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.data.Movie
import com.example.movieapp.ui.components.ErrorUi
import com.example.movieapp.ui.components.LoadingUi
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    onSelect: (String) -> Unit
) {
    var isSearching by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        SearchBarMovie(modifier = Modifier.onFocusChanged {
            isSearching = it.isFocused
        })
        if (isSearching) {
            when (val searchMovieUiState = homeViewModel.searchMovieUiState) {
                SearchMovieUiState.Fail -> ErrorUi()
                SearchMovieUiState.Loading -> LoadingUi()
                is SearchMovieUiState.Success -> GridMovies(
                    listMovie = searchMovieUiState.listMovies,
                    onSelect = onSelect
                )
            }
        } else {
            when (val homeUiState = homeViewModel.moviePopularUiState) {
                is MoviePopularUiState.Success -> {
                    GridMovies(
                        listMovie = homeUiState.listMovies,
                        onSelect = onSelect
                    )
                }

                MoviePopularUiState.Loading -> LoadingUi()
                MoviePopularUiState.Fail -> ErrorUi()
            }
        }
    }


}

@Composable
fun GridMovies(
    modifier: Modifier = Modifier,
    listMovie: List<Movie>,
    onSelect: (String) -> Unit
) {
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(paddingSmall),
        contentPadding = PaddingValues(paddingSmall)
    ) {
        items(listMovie) { movie ->
            ThumbnailMovieItem(
                modifier = Modifier
                    .fillMaxWidth().clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = { onSelect(movie.id.toString()) }),
                thumbnail = movie.posterPath,
            )
        }
    }

}

@Composable
fun ThumbnailMovieItem(modifier: Modifier = Modifier, thumbnail: String) {
    if (LocalInspectionMode.current) {
        Image(
            modifier = modifier,
            painter = painterResource(R.drawable.sample_thumbnail),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    } else {
        AsyncImage(
            modifier = modifier,
            model = "${Link.IMG_BASE}$thumbnail",
            contentDescription = null
        )
    }
}

private object Link {
    const val IMG_BASE = "https://image.tmdb.org/t/p/w500"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val moviePopular = Movie(
        id = 0,
        posterPath = "",
    )
    MovieAppTheme {
        Scaffold { innerPadding ->
            GridMovies(
                modifier = Modifier.padding(innerPadding),
                listMovie = (1..20).map { moviePopular },
                onSelect = {})
        }
    }
}

@Composable
fun SearchBarMovie(modifier: Modifier = Modifier) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = "",
        onValueChange = {},
        placeholder = { Text("Search") },
        prefix = {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
    )

}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    MovieAppTheme {
        Box(modifier = Modifier.background(color = Color.LightGray)) {
            SearchBarMovie(modifier = Modifier.padding(12.dp))
        }
    }
}