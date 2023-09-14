package com.yvillegas.movieapp.ui.movie.adapters.concat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yvillegas.movieapp.core.BaseConcatHolder
import com.yvillegas.movieapp.databinding.UpcomingMoviesBinding
import com.yvillegas.movieapp.ui.movie.adapters.MovieAdapter

class UpcomingConcatAdapter(private val movieAdapter: MovieAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = UpcomingMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder -> holder.bind(movieAdapter)
        }
    }

    private inner class ConcatViewHolder(val binding: UpcomingMoviesBinding): BaseConcatHolder<MovieAdapter>(binding.root){
        override fun bind(adapter: MovieAdapter) {
            binding.rvUpcoming.adapter = adapter
        }
    }
}