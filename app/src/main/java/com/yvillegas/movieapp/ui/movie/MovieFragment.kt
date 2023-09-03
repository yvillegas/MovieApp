package com.yvillegas.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.core.Resource
import com.yvillegas.movieapp.core.hide
import com.yvillegas.movieapp.core.show
import com.yvillegas.movieapp.data.local.AppDatabase
import com.yvillegas.movieapp.data.local.LocalMovieDataSource
import com.yvillegas.movieapp.data.model.Movie
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
import com.yvillegas.movieapp.ui.moviedetail.MovieDetailFragment

class MovieFragment : Fragment(R.layout.fragment_movie) , MovieAdapter.OnMovieClickListener{
    private lateinit var binding: FragmentMovieBinding

    private lateinit var navController: NavController

    private lateinit var concatAdapter: ConcatAdapter


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

        val navHostFragment = (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        //observeDestinationChange()


        concatAdapter = ConcatAdapter()

        initRecyclerView()

    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.movieFragment -> {
                    binding.bottomNavigationView.hide()
                }
                else -> {
                    binding.bottomNavigationView.show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        Log.d("LiveDataeeee", "init recycler")
        viewModel.getMovies().observe(viewLifecycleOwner) { result ->

            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("LiveDatasss", result.data.first.results!!.size.toString())
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(result.data.first.results!!, this@MovieFragment)
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(result.data.second.results!!, this@MovieFragment)
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(result.data.third.results!!, this@MovieFragment)
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
            movie.posterPath!!,
            movie.originalTitle!!,
            movie.voteAverage!!.toFloat(),
            movie.overview!!,
            movie.id!!,
            movie.releaseDate!!
        )
        findNavController().navigate(action)
    }
}