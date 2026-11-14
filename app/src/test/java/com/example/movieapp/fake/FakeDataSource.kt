package com.example.movieapp.fake

import com.example.movieapp.data.Genre
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieDetail
import com.example.movieapp.data.MovieResponse
import com.example.movieapp.data.VideoResponse
import com.example.movieapp.data.MovieVideo

object FakeDataSource {
    val listMovies = listOf(
        Movie(
            id = 0,
            posterPath = ""
        ),
        Movie(
            id = 1,
            posterPath = ""
        )
    )
    val popularMovieResponse = MovieResponse(
        results = listMovies
    )
    val searchResult = MovieResponse(
        results = listMovies
    )
    val movieDetail = MovieDetail(
        id = 27205,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 878, name = "Science Fiction"),
            Genre(id = 12, name = "Adventure")
        ),
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalTitle = "Inception",
        overview = "Dom Cobb is a skilled thief who steals secrets from people's subconscious during dreams.",
        releaseDate = "2010-07-15",
        videos = VideoResponse(
            results = listOf(
                MovieVideo(
                    key = "",
                    name = "Official Trailer",
                    type = "Trailer",
                    official = true
                )
            )
        )
    )
}