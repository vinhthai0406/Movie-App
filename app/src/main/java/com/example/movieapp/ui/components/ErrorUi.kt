package com.example.movieapp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorUi(modifier: Modifier = Modifier) {
    Text(
        text = "Không tải được danh sách phim",
    )
}