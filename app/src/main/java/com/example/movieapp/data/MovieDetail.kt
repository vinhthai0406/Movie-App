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
    val videos: VideoResult
)

@Serializable
class Genre(
    val id: Int,
    val name: String
)

@Serializable
class VideoResultItem(
    val key: String,
    val name: String,
    val type: String,
    val official: Boolean
)

@Serializable
class VideoResult(
    val results: List<VideoResultItem>
)