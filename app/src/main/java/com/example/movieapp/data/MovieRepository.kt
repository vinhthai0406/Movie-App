package com.example.movieapp.data

import com.example.movieapp.network.MovieApiService

interface MovieRepository {
    suspend fun getMoviePopular(): List<Movie>
    suspend fun getMovieDetail(movieId: String): MovieDetail
    suspend fun getSearchResult(searchText: String): List<Movie>
}

class DefaultMovieRepository(private val movieApiService: MovieApiService) : MovieRepository {
    override suspend fun getMoviePopular(): List<Movie> {
        val response = movieApiService.getMoviePopular()
        return response.results
    }

    override suspend fun getMovieDetail(movieId: String): MovieDetail =
        movieApiService.getMovieDetail(movieId = movieId)

    override suspend fun getSearchResult(searchText: String): List<Movie> {
        val response = movieApiService.getSearchResultMovie(searchText)
        return response.results
    }
}