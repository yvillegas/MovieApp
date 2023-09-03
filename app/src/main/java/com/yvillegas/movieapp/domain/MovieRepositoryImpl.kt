package com.yvillegas.movieapp.domain

import android.util.Log
import com.yvillegas.movieapp.core.InternetCheck
import com.yvillegas.movieapp.data.local.LocalMovieDataSource
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.model.MovieList
import com.yvillegas.movieapp.data.model.toMovieEntity
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
): MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getUpcomingMovies().results?.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getTopRatedMovies().results?.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("toprated"))
            }
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }
    }

    override suspend fun getPopularMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getPopularMovies().results?.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }

    /* override suspend fun getFavoriteMovies(): MovieList = dataSourceLocal.getFavoriteMovies()

    override suspend fun saveFavoriteMovies(movie: Movie) {
        dataSourceLocal.saveMovie(movie.toMovieEntity("favorite"))
    }
   override suspend fun getUpcomingMovies(): MovieList = dataSourceRemote.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSourceRemote.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSourceRemote.getPopularMovies()*/
}