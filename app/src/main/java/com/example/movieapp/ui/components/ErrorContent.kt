package com.example.movieapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun ErrorContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_small)
        )
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.error), contentDescription = null,
            colorFilter = ColorFilter.tint(color = Color(0xFFC0392B))
        )
        Text(
            text = stringResource(R.string.some_went_wrong),
            fontSize = 24.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    MovieAppTheme {
        ErrorContent(modifier = Modifier.fillMaxSize())
    }

}