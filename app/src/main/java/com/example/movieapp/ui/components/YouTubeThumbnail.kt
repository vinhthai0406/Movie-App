package com.example.movieapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun YouTubeThumbnail(
    modifier: Modifier = Modifier,
    videoId: String,
    videoName: String,
    onVideoClick: (String) -> Unit
) {
    val thumbnailUrl = "https://i.ytimg.com/vi/${videoId}/hqdefault.jpg"
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = { onVideoClick(videoId) })
    ) {

        AsyncImage(
            model = thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play $videoName",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun YoutubeThumbnailPreview() {
    MovieAppTheme {
        YouTubeThumbnail(videoId = "", videoName = "", onVideoClick = {})
    }
}