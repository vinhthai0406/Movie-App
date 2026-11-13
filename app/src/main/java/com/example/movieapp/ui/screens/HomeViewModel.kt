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
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    var moviePopularUiState: MoviePopularUiState by mutableStateOf(MoviePopularUiState.Success())
        private set
    var searchMovieUiState: SearchMovieUiState by mutableStateOf(SearchMovieUiState.Success())
        private set

    fun getPopularMovies() {
        moviePopularUiState = MoviePopularUiState.Loading
        viewModelScope.launch {
            moviePopularUiState = try {
                val listMovie = repository.getMoviePopular()
                MoviePopularUiState.Success(listMovie)
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                MoviePopularUiState.Fail
            }

        }
    }
    fun getSearchResultMovies(searchText: String){
        searchMovieUiState = SearchMovieUiState.Loading
        viewModelScope.launch {
            searchMovieUiState = try {
                val listMovies = repository.getSearchResult(searchText)
                SearchMovieUiState.Success(listMovies)
            } catch (e: Exception){
                SearchMovieUiState.Fail
            }
        }
    }
    init {
        getPopularMovies()
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

sealed interface MoviePopularUiState {
    data class Success(val listMovies: List<Movie> = listOf()) : MoviePopularUiState
    object Fail : MoviePopularUiState
    object Loading : MoviePopularUiState
}
sealed interface SearchMovieUiState {
    data class Success(val listMovies: List<Movie> = listOf()) : SearchMovieUiState
    object Fail : SearchMovieUiState
    object Loading : SearchMovieUiState
}
