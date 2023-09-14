package com.yvillegas.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.data.local.AppDatabase
import com.yvillegas.movieapp.data.local.LocalMovieDataSource
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.model.MovieList
import com.yvillegas.movieapp.data.remote.RemoteMovieDataSource
import com.yvillegas.movieapp.databinding.FragmentMovieBinding
import com.yvillegas.movieapp.domain.MovieRepositoryImpl
import com.yvillegas.movieapp.domain.RetrofitClient
import com.yvillegas.movieapp.presentation.MovieViewModel
import com.yvillegas.movieapp.presentation.MovieViewModelFactory
import com.yvillegas.movieapp.ui.movie.adapters.MovieAdapter
import com.yvillegas.movieapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.yvillegas.movieapp.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.yvillegas.movieapp.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener,
    MovieAdapter.OnMovieItemScroll {
    private lateinit var binding: FragmentMovieBinding

    private lateinit var concatAdapter: ConcatAdapter

    private lateinit var movieAdapters: List<MovieAdapter>

    private lateinit var movieLists: List<MovieList>

    private val mapMovie = mapOf("upcoming" to 0, "toprated" to 1, "popular" to 2)

    private var typeMovie = 1

    private lateinit var viewModelM: LiveData<Resource<MovieList>>

    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        initRecyclerView()


    }


    private fun initRecyclerView() {
        Log.d("LiveDataeeee", "init recycler")
        viewModel.getMovies().observe(viewLifecycleOwner) { result ->

            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }

                is Resource.Success -> {
                    movieLists = listOf(result.data.first, result.data.second, result.data.third)

                    binding.progressBar.visibility = View.GONE

                    movieAdapters = listOf(
                        MovieAdapter(
                            movieLists[0].results!!,
                            this@MovieFragment,
                            this@MovieFragment
                        ),
                        MovieAdapter(
                            movieLists[1].results!!,
                            this@MovieFragment,
                            this@MovieFragment
                        ),
                        MovieAdapter(
                            movieLists[2].results!!,
                            this@MovieFragment,
                            this@MovieFragment
                        ),
                    )

                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                movieAdapters[0]
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                movieAdapters[1]
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                movieAdapters[2]
                            )
                        )
                        binding.rvMovies.adapter = concatAdapter
                    }

                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("LiveDataeeee", result.exception.toString())
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.id,
            movie.adult,
            movie.backdropPath,
            movie.originalTitle,
            movie.originalLanguage,
            movie.overview,
            movie.popularity.toString(),
            movie.posterPath,
            movie.releaseDate,
            movie.video,
            movie.voteAverage.toFloat(),
            movie.voteCount
        )
        findNavController().navigate(action)
    }

    override fun OnMovieScroll(movie_type: String) {

        typeMovie = mapMovie[movie_type]!!.toInt()

        movieLists[typeMovie].page = movieLists[typeMovie].page ?: 1
        movieLists[typeMovie].page = movieLists[typeMovie].page!! + 1



        if (movie_type == "toprated")
            viewModelM = viewModel.getTopRatedMovies(movieLists[typeMovie].page!!)
        else if (movie_type == "upcoming")
            viewModelM = viewModel.getUpcomingMovies(movieLists[typeMovie].page!!)
        else
            viewModelM = viewModel.getPopularMovies(movieLists[typeMovie].page!!)

        viewModelM.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.d("LiveDataeeee", "cargandooo")
                }

                is Resource.Success -> {
                    movieLists[typeMovie].results.addAll(it.data.results)
                    movieAdapters[typeMovie].notifyItemInserted(movieLists[typeMovie].results.size - 1)
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