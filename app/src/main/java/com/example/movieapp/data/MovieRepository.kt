package com.example.movieapp.data

import com.example.movieapp.network.MovieApiService

interface MovieRepository {
    suspend fun getMoviePopular(): List<MoviePopular>
}

class DefaultMovieRepository(private val movieApiService: MovieApiService) : MovieRepository {
    override suspend fun getMoviePopular(): List<MoviePopular> {
        val response = movieApiService.getMoviePopular()
        return response.results
    }
}