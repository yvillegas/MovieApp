package com.yvillegas.movieapp.data.remote

import com.yvillegas.movieapp.application.AppConstants
import com.yvillegas.movieapp.data.model.MovieList
import com.yvillegas.movieapp.domain.WebService

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}