package com.example.movieapp.fake

import com.example.movieapp.data.MovieDetail
import com.example.movieapp.data.MovieResponse
import com.example.movieapp.network.MovieApiService

class FakeMovieAppService : MovieApiService {
    override suspend fun getMoviePopular(apiKey: String): MovieResponse =
        FakeDataSource.popularMovieResponse

    override suspend fun getMovieDetail(
        movieId: String,
        apiKey: String,
        appendToResponse: String
    ): MovieDetail = FakeDataSource.movieDetail

    override suspend fun getSearchResultMovie(searchText: String): MovieResponse =
        FakeDataSource.searchResult
}