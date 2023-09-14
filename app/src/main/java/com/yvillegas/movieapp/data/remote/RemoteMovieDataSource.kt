package com.yvillegas.movieapp.data.remote

import com.yvillegas.movieapp.application.AppConstants
import com.yvillegas.movieapp.data.model.CastList
import com.yvillegas.movieapp.data.model.MovieList
import com.yvillegas.movieapp.domain.WebService

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(page: Int): MovieList = webService.getUpcomingMovies(page,AppConstants.API_KEY)

    suspend fun getTopRatedMovies(page: Int): MovieList = webService.getTopRatedMovies(page,AppConstants.API_KEY)

    suspend fun getPopularMovies(page: Int): MovieList = webService.getPopularMovies(page,AppConstants.API_KEY)
    suspend fun getCastMovie(id: String): CastList = webService.getCastMovie(id, AppConstants.API_KEY)


}