package com.yvillegas.movieapp.ui.moviedetail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.core.hide
import com.yvillegas.movieapp.core.show
import com.yvillegas.movieapp.data.local.AppDatabase
import com.yvillegas.movieapp.data.local.LocalMovieDataSource
import com.yvillegas.movieapp.data.model.CastList
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource
import com.yvillegas.movieapp.databinding.FragmentMovieDetailBinding
import com.yvillegas.movieapp.domain.MovieRepositoryImpl
import com.yvillegas.movieapp.domain.RetrofitClient
import com.yvillegas.movieapp.presentation.CastViewModel
import com.yvillegas.movieapp.presentation.CastViewModelFactory
import com.yvillegas.movieapp.presentation.FavoriteViewModel
import com.yvillegas.movieapp.presentation.MovieViewModelFactory
import com.yvillegas.movieapp.ui.moviedetail.adapters.CastAdapter


class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    private lateinit var castList: CastList
    private val colorStar = intArrayOf(Color.rgb(255, 195, 0), Color.rgb(255, 255, 255))
    private var flagStar: Boolean = false
    private lateinit var movieNew: Movie

    private val viewModel by viewModels<CastViewModel> {
        CastViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    private val viewModelF by viewModels<FavoriteViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isFavorite()
        getMovieDetail(view)

        initRecycleViewCast(args.id.toString())

        binding.btnAddFavorite.setOnClickListener {
            if(!flagStar) addMovieFavorite() else delMovieFavorite()
        }
    }

    private fun isFavorite() {
        viewModelF.getIsFavorite(args.id).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    if (it.data > 0) flagStar = true
                    getFavoriteStar()
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

    private fun getFavoriteStar() {
        binding.btnAddFavorite.setBackgroundResource(
            if (flagStar) R.drawable.baseline_star_24_white else R.drawable.baseline_star_border_24
        )
    }

    private fun addMovieFavorite() {

        movieNew = Movie(
            args.id,
            args.adult,
            args.backdropPath,
            args.originalTitle,
            args.originalLanguage,
            args.overview,
            args.popularity.toDouble(),
            args.posterPath,
            args.releaseDate,
            args.video,
            args.voteAverage.toDouble(),
            args.voteCount,
            "favorite"
        )

        viewModelF.addFavoriteMovie(movieNew).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {

                    flagStar = !flagStar
                    Toast.makeText(
                        requireContext(),
                        "Se agregó a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                    getFavoriteStar()
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

    private fun delMovieFavorite() {

        viewModelF.delFavoriteMovie(args.id).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {

                    flagStar = !flagStar
                    Toast.makeText(
                        requireContext(),
                        "Se elimin'o a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                    getFavoriteStar()
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