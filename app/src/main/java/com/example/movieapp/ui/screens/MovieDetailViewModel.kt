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
import com.example.movieapp.data.MovieDetail
import com.example.movieapp.data.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieRepository) : ViewModel() {
    var uiState: MovieDetailUiState by mutableStateOf(MovieDetailUiState.Success())
        private set

    fun getMovieDetail(movieId: String) {
        uiState = MovieDetailUiState.Loading
        viewModelScope.launch {
            uiState = try {
                val movieDetail = repository.getMovieDetail(movieId)
                MovieDetailUiState.Success(movieDetail)
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                MovieDetailUiState.Fail
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as MovieAppApplication).container
                val repository = container.movieRepository
                MovieDetailViewModel(repository)
            }
        }
    }
}

sealed interface MovieDetailUiState {
    data class Success(val movieDetail: MovieDetail? = null) : MovieDetailUiState
    object Loading : MovieDetailUiState
    object Fail : MovieDetailUiState
}