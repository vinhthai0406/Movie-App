package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = Modifier.size(100.dp))
}

@Preview(showBackground = true)
@Composable
private fun LoadingContentPreview() {
    LoadingContent(modifier = Modifier.fillMaxSize())
}