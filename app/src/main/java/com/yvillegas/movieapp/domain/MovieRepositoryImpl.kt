package com.yvillegas.movieapp.domain

import android.util.Log
import com.yvillegas.movieapp.core.InternetCheck
import com.yvillegas.movieapp.data.local.LocalMovieDataSource
import com.yvillegas.movieapp.data.model.CastList
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.model.MovieList
import com.yvillegas.movieapp.data.model.toMovieEntity
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {
    /*override suspend fun getUpcomingMovies(page: Int): MovieList {

        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getUpcomingMovies(page).results?.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovies(page: Int): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getTopRatedMovies(page).results?.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("toprated"))
            }
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }
    }

    override suspend fun getPopularMovies(page: Int): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getPopularMovies(page).results?.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }
    */
    override suspend fun getUpcomingMovies(page: Int): MovieList {
        var data = dataSourceRemote.getUpcomingMovies(page)
        data.results?.forEach { movie ->
            movie.movieType = "upcoming"
        }
        return data
    }

    override suspend fun getTopRatedMovies(page: Int): MovieList {
        var data = dataSourceRemote.getTopRatedMovies(page)
        data.results?.forEach { movie ->
            movie.movieType = "toprated"
        }
        return data
    }

    override suspend fun getPopularMovies(page: Int): MovieList {
        var data = dataSourceRemote.getPopularMovies(page)
        data.results?.forEach { movie ->
            movie.movieType = "popular"
        }
        return data

    }


    override suspend fun getCastMovie(id: String): CastList = dataSourceRemote.getCastMovie(id)
    override suspend fun getFavoriteMovies(): MovieList = dataSourceLocal.getFavoriteMovies()
    override suspend fun addFavoriteMovies(movie: Movie) {
        dataSourceLocal.addFavoriteMovies(movie.toMovieEntity("favorite"))
    }

    override suspend fun delFavoriteMovies(id: Int) {
        dataSourceLocal.delFavoriteMovies(id)
    }

    override suspend fun getIsFavorite(id: Int): Int = dataSourceLocal.getIsFavorite(id)


    /* override suspend fun getFavoriteMovies(): MovieList = dataSourceLocal.getFavoriteMovies()

    override suspend fun saveFavoriteMovies(movie: Movie) {
        dataSourceLocal.saveMovie(movie.toMovieEntity("favorite"))
    }
   override suspend fun getUpcomingMovies(): MovieList = dataSourceRemote.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSourceRemote.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSourceRemote.getPopularMovies()*/
}