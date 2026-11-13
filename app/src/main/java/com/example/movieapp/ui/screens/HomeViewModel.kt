package com.example.movieapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movieapp.data.AppContainer
import com.example.movieapp.data.MoviePopular
import com.example.movieapp.data.MovieRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState.Success())
        private set

    fun getMovies() {
        uiState = HomeUiState.Loading
        viewModelScope.launch {
            uiState = try {
                val listMovie = repository.getMoviePopular()
                HomeUiState.Success(listMovie)
            } catch (e: Exception) {
                HomeUiState.Fail
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val movieRepository = (this[APPLICATION_KEY] as AppContainer).movieRepository
                HomeViewModel(repository = movieRepository)
            }
        }
    }

}

interface HomeUiState {
    data class Success(val listMovies: List<MoviePopular> = listOf()) : HomeUiState
    object Fail : HomeUiState
    object Loading : HomeUiState
}