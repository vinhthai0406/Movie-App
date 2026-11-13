package com.example.movieapp.network

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.MovieDetail
import com.example.movieapp.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("append_to_response") appendToResponse: String = "videos"
    ): MovieDetail

    @GET("search/movie")
    suspend fun getSearchResultMovie(@Query("query") searchText: String): MovieResponse
}