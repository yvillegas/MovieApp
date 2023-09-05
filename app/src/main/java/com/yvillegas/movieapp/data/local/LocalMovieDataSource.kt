package com.yvillegas.movieapp.data.local

import android.util.Log
import com.yvillegas.movieapp.data.model.MovieList
import com.yvillegas.movieapp.data.model.entities.MovieEntity
import com.yvillegas.movieapp.data.model.entities.toMovieList

class LocalMovieDataSource(private val movieDao: MovieDao) {
    suspend fun getUpcomingMovies(): MovieList
    {
        return movieDao.getAllMovies().filter { it.movieType == "upcoming" }.toMovieList()
    }

    suspend fun getTopRatedMovies(): MovieList
    {
        return movieDao.getAllMovies().filter { it.movieType == "toprated" }.toMovieList()
    }

    suspend fun getPopularMovies(): MovieList
    {
        return movieDao.getAllMovies().filter { it.movieType == "popular" }.toMovieList()
    }

    suspend fun getFavoriteMovies(): MovieList
    {
        return movieDao.getAllMovies().filter { it.favorite == "true" }.toMovieList()
    }


    suspend fun saveMovie(movie: MovieEntity){
        movieDao.saveMovie(movie)
    }

    suspend fun addFavoriteMovies(id: String, flag:String){
        movieDao.addFavorite(id , flag)
    }
    suspend fun deleteFavoriteMovies(id: String){
        movieDao.deleteFavorite(id)
    }
}