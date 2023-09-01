package com.yvillegas.movieapp.domain

import com.yvillegas.movieapp.data.model.MovieList
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(private val dataSource: RemoteMovieDataSource): MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList =  dataSource.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()
}