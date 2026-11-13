package com.example.movieapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieResponse(
    val results: List<Movie>
)

@Serializable
class Movie(
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("poster_path")
    val posterPath: String,
)