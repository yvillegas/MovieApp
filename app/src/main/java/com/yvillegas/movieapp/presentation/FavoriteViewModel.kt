package com.yvillegas.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FavoriteViewModel(private val repository: MovieRepository): ViewModel() {
    fun addFavoriteMovie(id: String, flag:String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.addFavoriteMovies(id,flag)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun getFavoriteMovie() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getFavoriteMovies()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class FavoriteViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)
    }
}