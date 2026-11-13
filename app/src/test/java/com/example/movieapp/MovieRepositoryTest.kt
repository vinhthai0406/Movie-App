package com.example.movieapp

import com.example.movieapp.data.DefaultMovieRepository
import com.example.movieapp.fake.FakeDataSource
import com.example.movieapp.fake.FakeMovieAppService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieRepositoryTest {
    @Test
    fun movieRepositoryTest_getPopularMovie_verifyListMovie() = runTest {
        val repositoryTest = DefaultMovieRepository(movieApiService = FakeMovieAppService())
        val listMovies = repositoryTest.getMoviePopular()
        assertEquals(FakeDataSource.listMovies, listMovies)
    }
    @Test
    fun movieRepositoryTest_searchMovie_verifyListMovieResult() = runTest {
        val repositoryTest = DefaultMovieRepository(movieApiService = FakeMovieAppService())
        val result = repositoryTest.getSearchResult("")
        assertEquals(FakeDataSource.searchResult.results, result )
    }
    @Test
    fun movieRepositoryTest_getMovieDetail_verifyMovieDetail() = runTest {
        val repositoryTest = DefaultMovieRepository(movieApiService = FakeMovieAppService())
        val result = repositoryTest.getMovieDetail("")
        assertEquals(FakeDataSource.movieDetail, result )
    }

}