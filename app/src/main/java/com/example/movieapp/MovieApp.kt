package com.example.movieapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.ui.screens.HomeViewModel

@Composable
fun MovieApp(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    HomeScreen(homeViewModel = homeViewModel)

}