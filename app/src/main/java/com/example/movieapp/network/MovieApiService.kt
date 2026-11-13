package com.example.movieapp.network

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.MoviePopular
import com.example.movieapp.data.MoviePopularResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): MoviePopularResponse
}