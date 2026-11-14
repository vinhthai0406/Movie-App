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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    var popularMoviesUiState: PopularMoviesUiState by mutableStateOf(PopularMoviesUiState.Success())
        private set
    var searchMovieUiState: SearchMovieUiState by mutableStateOf(SearchMovieUiState.Success())
        private set
    var searchInput = MutableStateFlow("")
        private set
    val searchInputUiState = searchInput.asStateFlow()

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

    @OptIn(FlowPreview::class)
    private fun observeSearchText() {
        viewModelScope.launch {
            searchInput.onEach { query ->
                searchMovieUiState = if (query.isBlank()) {
                    SearchMovieUiState.Success()
                } else {
                    SearchMovieUiState.Loading
                }
            }.debounce(500.milliseconds).distinctUntilChanged().collect { query ->
                if (query.isEmpty()) return@collect
                searchMovieUiState = try {
                    val listMovies = repository.getSearchResult(query)
                    if (listMovies.isEmpty()) {
                        SearchMovieUiState.NoResult
                    } else {
                        SearchMovieUiState.Success(listMovies)
                    }
                } catch (e: Exception) {
                    SearchMovieUiState.Fail
                }
            }
        }
    }

    fun updateSearchText(text: String) {
        searchInput.value = text
    }

    init {
        getPopularMovies()
        observeSearchText()
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
    object NoResult : SearchMovieUiState
    object Fail : SearchMovieUiState
    object Loading : SearchMovieUiState
}
