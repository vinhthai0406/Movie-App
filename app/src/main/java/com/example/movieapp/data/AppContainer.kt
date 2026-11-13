package com.example.movieapp.data

import com.example.movieapp.network.MovieApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val movieRepository: MovieRepository
}

class MovieAppContainer() : AppContainer {
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()
    private val movieApiService by lazy { retrofit.create(MovieApiService::class.java) }

    override val movieRepository: MovieRepository = DefaultMovieRepository(movieApiService)
}