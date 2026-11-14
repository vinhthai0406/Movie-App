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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.movieapp.data.VideoResponse
import com.example.movieapp.data.MovieVideo
import com.example.movieapp.ui.components.ErrorContent
import com.example.movieapp.ui.components.LoadingContent
import com.example.movieapp.ui.components.YouTubeThumbnail
import com.example.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    title: String,
    movieDetailUiState: MovieDetailUiState,
    onPlay: (String) -> Unit,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(title) }, navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            })
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            when (movieDetailUiState) {
                MovieDetailUiState.Fail -> ErrorContent()
                MovieDetailUiState.Loading -> LoadingContent()
                is MovieDetailUiState.Success -> {
                    val movieDetail = movieDetailUiState.movieDetail
                    if (movieDetail != null) {
                        MovieDetailUi(movieDetail = movieDetail, onPlay = onPlay)
                    } else {
                        ErrorContent()
                    }
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
        videos = VideoResponse(
            results = listOf(
                MovieVideo(
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