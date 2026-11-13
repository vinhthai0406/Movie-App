package com.example.movieapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.data.Movie
import com.example.movieapp.ui.IMG_BASE
import com.example.movieapp.ui.components.ErrorContent
import com.example.movieapp.ui.components.LoadingContent
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    moviePopularUiState: MoviePopularUiState,
    searchMovieUiState: SearchMovieUiState,
    onSelect: (String, String) -> Unit,
    onSearching: (String) -> Unit,
) {
    var isSearching by remember { mutableStateOf(false) }
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(paddingSmall)
        ) {
            SearchBarMovie(
                modifier = Modifier
                    .onFocusChanged {
                        isSearching = it.isFocused
                    }
                    .padding(vertical = paddingSmall), onValueChange = onSearching)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (isSearching) {

                    when (searchMovieUiState) {
                        SearchMovieUiState.Fail -> ErrorContent()
                        SearchMovieUiState.Loading -> LoadingContent()
                        is SearchMovieUiState.Success -> {
                            Log.d("searching", searchMovieUiState.listMovies.toString())

                            GridMovies(

                                listMovie = searchMovieUiState.listMovies,
                                onSelect = onSelect
                            )
                        }
                    }
                } else {
                    when (moviePopularUiState) {
                        is MoviePopularUiState.Success -> {
                            GridMovies(
                                listMovie = moviePopularUiState.listMovies,
                                onSelect = onSelect
                            )
                        }

                        MoviePopularUiState.Loading -> LoadingContent()
                        MoviePopularUiState.Fail -> ErrorContent()
                    }
                }
            }
        }
    }


}

@Composable
fun GridMovies(
    modifier: Modifier = Modifier,
    listMovie: List<Movie>,
    onSelect: (String, String) -> Unit
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
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = { onSelect(movie.id.toString(), movie.originalTitle) }),
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
            model = "${IMG_BASE}$thumbnail",
            contentDescription = null
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val moviePopular = Movie(
        id = 0,
        originalTitle = "",
        posterPath = "",
    )
    MovieAppTheme {
        HomeScreen(
            moviePopularUiState = MoviePopularUiState.Success(listMovies = (1..20).map { moviePopular }),
            searchMovieUiState = SearchMovieUiState.Success(listMovies = (1..20).map { moviePopular }),
            onSelect = { string, string1 -> }, onSearching = {})
    }
}

@Composable
fun SearchBarMovie(modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current
    var isFocus by remember { mutableStateOf(false) }
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { isFocus = it.isFocused },
            value = searchText,
            onValueChange = { newValue ->
                onValueChange(newValue)
                searchText = newValue
            },
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
        if (isFocus) {
            TextButton(onClick = { localFocusManager.clearFocus() }) { Text("Cancel") }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    MovieAppTheme {
        Box(modifier = Modifier.background(color = Color.LightGray)) {
            SearchBarMovie(
                modifier = Modifier.padding(12.dp),
                onValueChange = { },
            )
        }
    }
}