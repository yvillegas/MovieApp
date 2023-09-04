package com.yvillegas.movieapp.ui.movie.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yvillegas.movieapp.core.BaseViewHolder
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.databinding.MovieItemBinding

class MovieAdapter(private val movieList: List<Movie>, private val itemClickListener: OnMovieClickListener) : RecyclerView.Adapter<BaseViewHolder<*>>(){
    interface OnMovieClickListener{
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        Log.d("LiveDatasss", "create holder")
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        Log.d("LiveDatasss", "create holder")
        val holder = MovieViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onMovieClick(movieList[position])
        }
        return holder
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MovieViewHolder -> holder.bind(movieList[position])
        }
    }

    private inner class MovieViewHolder(
        val binding: MovieItemBinding,
        val context: Context,
    ): BaseViewHolder<Movie>(binding.root){

        override fun bind(item: Movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.posterPath}").centerCrop().into(binding.imgMovie)
            binding.titleMovie.text = item.originalTitle.toString()
            binding.dateMovie.text = item.releaseDate
        }
    }

}