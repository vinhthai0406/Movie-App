package com.example.movieapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("release_date")
    val releaseDate: String,
    val videos: VideoResponse
)

@Serializable
class Genre(
    val id: Int,
    val name: String
)

@Serializable
class MovieVideo(
    val key: String,
    val name: String,
    val type: String,
    val official: Boolean
)

@Serializable
class VideoResponse(
    val results: List<MovieVideo>
)