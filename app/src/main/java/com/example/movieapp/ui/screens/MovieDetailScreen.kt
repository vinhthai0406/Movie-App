package com.example.movieapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.data.Genre
import com.example.movieapp.data.MovieDetail
import com.example.movieapp.data.VideoResult
import com.example.movieapp.data.VideoResultItem
import com.example.movieapp.ui.components.ErrorUi
import com.example.movieapp.ui.components.LoadingUi
import com.example.movieapp.ui.components.YouTubeThumbnail
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel,
    onPlay: (String) -> Unit
) {
    Box(modifier = modifier) {
        when (val state = viewModel.uiState) {
            MovieDetailUiState.Fail -> ErrorUi()
            MovieDetailUiState.Loading -> LoadingUi()
            is MovieDetailUiState.Success -> {
                val movieDetail = state.movieDetail
                if (movieDetail != null) {
                    MovieDetailUi(movieDetail = movieDetail, onPlay = onPlay)
                } else {
                    ErrorUi()
                }
            }
        }
    }

}

@Composable
fun MovieDetailUi(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    onPlay: (String) -> Unit
) {
    val genres = movieDetail.genres.joinToString(separator = " , ") { it.name }
    val trailer = movieDetail.videos.results.firstOrNull {
        it.type.lowercase(LocalLocale.current.platformLocale) == "trailer" && it.official
    }
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(color = Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            if (LocalInspectionMode.current) {
                Image(
                    modifier = modifier,
                    painter = painterResource(R.drawable.sample_youtube_thumbnail),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            } else {
                if (trailer != null) {
                    YouTubeThumbnail(
                        videoId = trailer.key,
                        videoName = trailer.name,
                        onVideoClick = { onPlay(trailer.key) }
                    )
                }
            }

        }
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
            Text(movieDetail.originalTitle, style = MaterialTheme.typography.displaySmall)
            Row {
                Text("Genres: ", style = MaterialTheme.typography.titleMedium)
                Text(genres)
            }
            Row {
                Text("Overview: ${movieDetail.overview}")
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenPreview() {
    val inceptionDetail = MovieDetail(
        id = 27205,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 878, name = "Science Fiction"),
            Genre(id = 12, name = "Adventure")
        ),
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalTitle = "Inception",
        overview = "Dom Cobb is a skilled thief who steals secrets from people's subconscious during dreams.",
        releaseDate = "2010-07-15",
        videos = VideoResult(
            results = listOf(
                VideoResultItem(
                    key = "",
                    name = "Official Trailer",
                    type = "Trailer",
                    official = true
                )
            )
        )
    )
    MovieAppTheme {
        MovieDetailUi(modifier = Modifier.fillMaxSize(), movieDetail = inceptionDetail, onPlay = {})
    }

}