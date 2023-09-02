package com.yvillegas.movieapp.ui.movie.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource
import com.yvillegas.movieapp.databinding.FragmentMovieBinding
import com.yvillegas.movieapp.domain.MovieRepository
import com.yvillegas.movieapp.domain.MovieRepositoryImpl
import com.yvillegas.movieapp.domain.RetrofitClient
import com.yvillegas.movieapp.domain.WebService
import com.yvillegas.movieapp.presentation.MovieViewModel
import com.yvillegas.movieapp.presentation.MovieViewModelFactory

class MovieFragment : Fragment(R.layout.fragment_movie) , MovieAdapter.OnMovieClickListener{
    private lateinit var binding: FragmentMovieBinding

    private val viewModel by viewModels<MovieViewModel> { MovieViewModelFactory( MovieRepositoryImpl(RemoteMovieDataSource(RetrofitClient.webservice)) ) }

    private lateinit var concatAdapter: ConcatAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        concatAdapter = ConcatAdapter()


    }

    override fun onMovieClick(movie: Movie) {
        TODO("Not yet implemented")
    }
}