package com.yvillegas.movieapp.ui.moviefavorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.core.hide
import com.yvillegas.movieapp.core.show
import com.yvillegas.movieapp.data.local.AppDatabase
import com.yvillegas.movieapp.data.local.LocalMovieDataSource
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource
import com.yvillegas.movieapp.databinding.FragmentMovieFavoritesBinding
import com.yvillegas.movieapp.domain.MovieRepositoryImpl
import com.yvillegas.movieapp.domain.RetrofitClient
import com.yvillegas.movieapp.presentation.FavoriteViewModel
import com.yvillegas.movieapp.presentation.MovieViewModelFactory
import com.yvillegas.movieapp.ui.movie.MovieFragmentDirections
import com.yvillegas.movieapp.ui.movie.adapters.MovieAdapter
import com.yvillegas.movieapp.ui.moviefavorites.adapters.FavoriteAdapter


class MovieFavoritesFragment : Fragment(R.layout.fragment_movie_favorites), FavoriteAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieFavoritesBinding

    private val viewModel by viewModels<FavoriteViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieFavoritesBinding.bind(view)

        viewModel.getFavoriteMovie().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    binding.rvMoviesFavorite.adapter = FavoriteAdapter(it.data.results, this@MovieFavoritesFragment)
                }

                is Resource.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

   override fun onMovieClick(movie: Movie) {
        val action = MovieFavoritesFragmentDirections.actionMovieFavoritesFragmentToMovieDetailFragment(
            movie.posterPath!!,
            movie.originalTitle!!,
            movie.voteAverage!!.toFloat(),
            movie.overview!!,
            movie.id!!,
            movie.releaseDate!!,
            movie.favorite!!
        )
        findNavController().navigate(action)
   }

}