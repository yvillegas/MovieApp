package com.yvillegas.movieapp.ui.moviedetail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yvillegas.movieapp.core.BaseViewHolder
import com.yvillegas.movieapp.data.model.Cast
import com.yvillegas.movieapp.databinding.CastItemBinding

class CastAdapter(private val castList: List<Cast>?): RecyclerView.Adapter<BaseViewHolder<*>>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = CastItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        val holder = CastViewHolder(itemBinding, parent.context)
        return holder
    }

    override fun getItemCount(): Int = castList!!.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is CastViewHolder -> holder.bind(castList!![position])
        }
    }

    private inner class CastViewHolder(
        val binding: CastItemBinding,
        val context: Context,
    ): BaseViewHolder<Cast>(binding.root){

        override fun bind(item: Cast) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.profilePath}").centerCrop().into(binding.imgCast)
            binding.nameCast.text = item.name
            binding.characterCast.text = item.character
        }
    }
}