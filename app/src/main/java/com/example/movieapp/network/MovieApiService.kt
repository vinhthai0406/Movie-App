package com.example.movieapp.network

import com.example.movieapp.data.MoviePopular
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(): List<MoviePopular>
}