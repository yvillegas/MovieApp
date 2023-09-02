package com.yvillegas.movieapp.domain

import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.model.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
    //suspend fun getFavoriteMovies(): MovieList
    //suspend fun saveFavoriteMovies(movie: Movie)
}