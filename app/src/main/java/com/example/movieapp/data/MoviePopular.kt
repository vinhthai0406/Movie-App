package com.example.movieapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MoviePopularResponse(
    val results: List<MoviePopular>
)

@Serializable
class MoviePopular(
    val id: Int,
    val title: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String
)


//{
//    "adult": false,
//    "backdrop_path": "/8Tfys3mDZVp4tNoH2ktm06a0Tau.jpg",
//    "genre_ids": [
//    878,
//    12
//    ],
//    "id": 687163,
//    "title": "Project Hail Mary",
//    "original_language": "en",
//    "original_title": "Project Hail Mary",
//    "overview": "Science teacher Ryland Grace wakes up on a spaceship light years from home with no recollection of who he is or how he got there. As his memory returns, he begins to uncover his mission: solve the riddle of the mysterious substance causing the sun to die out. He must call on his scientific knowledge and unorthodox ideas to save everything on Earth from extinction.",
//    "popularity": 1071.9478,
//    "poster_path": "/iOb2fjXLbpJgyQXe46n1WtGCnaa.jpg",
//    "release_date": "2026-03-15",
//    "softcore": false,
//    "video": false,
//    "vote_average": 8.627,
//    "vote_count": 8824
//},