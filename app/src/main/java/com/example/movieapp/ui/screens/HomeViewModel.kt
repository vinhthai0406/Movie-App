package com.example.movieapp.ui.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movieapp.MovieAppApplication
import com.example.movieapp.data.MoviePopular
import com.example.movieapp.data.MovieRepository
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
                Log.e(TAG, e.message.toString())
                HomeUiState.Fail
            }

        }
    }
    init {
        getMovies()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as MovieAppApplication).container
                val movieRepository = container.movieRepository
                HomeViewModel(repository = movieRepository)
            }
        }
    }

}

sealed interface HomeUiState {
    data class Success(val listMovies: List<MoviePopular> = listOf()) : HomeUiState
    object Fail : HomeUiState
    object Loading : HomeUiState
}