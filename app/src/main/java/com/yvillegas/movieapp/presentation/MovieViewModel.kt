package com.yvillegas.movieapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MovieViewModel(private val repository: MovieRepository): ViewModel() {
    fun getMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(Triple(repository.getUpcomingMovies(1), repository.getTopRatedMovies(1) , repository.getPopularMovies(1))))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun getUpcomingMovies(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getUpcomingMovies(page)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun getTopRatedMovies(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getTopRatedMovies(page) ))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun getPopularMovies(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getPopularMovies(page)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class MovieViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)
    }
}