package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel) {
    Scaffold(modifier = modifier) { innerPadding ->
        ListMovie(modifier = Modifier.padding(innerPadding))
    }

}

@Composable
fun ListMovie(modifier: Modifier = Modifier) {


}

@Composable
fun MovieItem(modifier: Modifier = Modifier) {

}