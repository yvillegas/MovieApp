package com.yvillegas.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CastViewModel(private val repository: MovieRepository): ViewModel() {
    fun getCastMovies(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getCastMovie(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

class CastViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)
    }
}