package com.yvillegas.movieapp.ui.moviefavorites.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yvillegas.movieapp.core.BaseViewHolder
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.databinding.FavoriteItemBinding

class FavoriteAdapter(private val favoriteList: List<Movie>?): RecyclerView.Adapter<BaseViewHolder<*>>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        val holder = FavoriteViewHolder(itemBinding, parent.context)
        return holder
    }

    override fun getItemCount(): Int = favoriteList!!.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is FavoriteViewHolder -> holder.bind(favoriteList!![position])
        }
    }

    private inner class FavoriteViewHolder(
        val binding: FavoriteItemBinding,
        val context: Context,
    ): BaseViewHolder<Movie>(binding.root){

        override fun bind(item: Movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.posterPath}").centerCrop().into(binding.imgMovie)
            binding.titleMovie.text = item.originalTitle
            binding.dateMovie.text = item.releaseDate
        }
    }
}