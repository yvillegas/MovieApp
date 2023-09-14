package com.yvillegas.movieapp.domain

import com.yvillegas.movieapp.data.model.Cast
import com.yvillegas.movieapp.data.model.CastList
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.model.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies(page:Int): MovieList
    suspend fun getTopRatedMovies(page:Int): MovieList
    suspend fun getPopularMovies(page:Int): MovieList
    suspend fun getCastMovie(id: String): CastList
    suspend fun getFavoriteMovies(): MovieList
    suspend fun getIsFavorite(id: Int): Int
    suspend fun addFavoriteMovies(movie: Movie)
    suspend fun delFavoriteMovies(id: Int)
}