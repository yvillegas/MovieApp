package com.yvillegas.movieapp.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterPath}").centerCrop().into(binding.imgMovie)
        binding.titleMovie.text = args.originalTitle
        binding.dateMovie.text = args.releaseDate
        binding.averageMovie.text = args.voteAverage.toString()
        binding.overviewMovie.text = args.overview


    }



}