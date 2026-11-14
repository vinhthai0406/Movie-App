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
    var popularMoviesUiState: PopularMoviesUiState by mutableStateOf(PopularMoviesUiState.Success())
        private set
    var searchMovieUiState: SearchMovieUiState by mutableStateOf(SearchMovieUiState.Success())
        private set

    fun getPopularMovies() {
        popularMoviesUiState = PopularMoviesUiState.Loading
        viewModelScope.launch {
            popularMoviesUiState = try {
                val listMovie = repository.getMoviePopular()
                PopularMoviesUiState.Success(listMovie)
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                PopularMoviesUiState.Fail
            }

        }
    }
    fun getSearchResultMovies(searchText: String){
        Log.i("getSearchResultMovies", searchText)
        if(searchText.isEmpty()) return
        searchMovieUiState = SearchMovieUiState.Loading
        viewModelScope.launch {
//            delay(5_00.milliseconds)
            searchMovieUiState = try {
                val listMovies = repository.getSearchResult(searchText)
                Log.i("listMovies size", listMovies.size.toString())
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

sealed interface PopularMoviesUiState {
    data class Success(val listMovies: List<Movie> = listOf()) : PopularMoviesUiState
    object Fail : PopularMoviesUiState
    object Loading : PopularMoviesUiState
}
sealed interface SearchMovieUiState {
    data class Success(val movies: List<Movie> = listOf()) : SearchMovieUiState
    object Fail : SearchMovieUiState
    object Loading : SearchMovieUiState
}
