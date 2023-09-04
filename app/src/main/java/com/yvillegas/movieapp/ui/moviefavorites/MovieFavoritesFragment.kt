package com.yvillegas.movieapp.ui.moviefavorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yvillegas.movieapp.R
import com.yvillegas.movieapp.databinding.FragmentMovieBinding
import com.yvillegas.movieapp.databinding.FragmentMovieFavoritesBinding


class MovieFavoritesFragment : Fragment(R.layout.fragment_movie_favorites) {

    private lateinit var binding: FragmentMovieFavoritesBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieFavoritesBinding.bind(view)
    }

}