package com.yvillegas.movieapp.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.core.hide
import com.yvillegas.movieapp.core.show
import com.yvillegas.movieapp.data.local.AppDatabase
import com.yvillegas.movieapp.data.local.LocalMovieDataSource
import com.yvillegas.movieapp.data.model.CastList
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource
import com.yvillegas.movieapp.databinding.FragmentMovieDetailBinding
import com.yvillegas.movieapp.domain.MovieRepositoryImpl
import com.yvillegas.movieapp.domain.RetrofitClient
import com.yvillegas.movieapp.presentation.CastViewModel
import com.yvillegas.movieapp.presentation.CastViewModelFactory
import com.yvillegas.movieapp.presentation.FavoriteViewModel
import com.yvillegas.movieapp.presentation.MovieViewModel
import com.yvillegas.movieapp.presentation.MovieViewModelFactory
import com.yvillegas.movieapp.ui.moviedetail.adapters.CastAdapter


class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    private lateinit var castList: CastList
    private lateinit var llManager: GridLayoutManager

    private val viewModel by viewModels<CastViewModel> {
        CastViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    private val viewModelM by viewModels<FavoriteViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieDetail(view)

        initRecycleViewCast(args.id.toString())

        binding.btnAddFavorite.setOnClickListener{
            addMovieFavorite(args.id.toString())
        }
    }

    private fun initRecycleViewCast(id: String) {
        viewModel.getCastMovies(id).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    castList = it.data
                    binding.rvCastMovie.adapter = CastAdapter(castList.cast)

                }

                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun getMovieDetail(view: View) {
        binding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterPath}")
            .centerCrop().into(binding.imgMovie)
        binding.titleMovie.text = args.originalTitle
        binding.dateMovie.text = args.releaseDate
        binding.averageMovie.text = args.voteAverage.toString()
        binding.overviewMovie.text = args.overview
    }

    private fun addMovieFavorite(id: String) {
        viewModelM.addFavoriteMovie(id).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Se agregó a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

}