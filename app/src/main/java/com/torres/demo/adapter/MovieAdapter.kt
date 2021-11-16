package com.torres.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.torres.demo.R
import com.torres.demo.data.model.MovieDao
import com.torres.demo.databinding.RowMovieBinding
import com.torres.demo.utilities.Constants
import com.torres.demo.vo.BaseViewHolder

class MovieAdapter(var items:ArrayList<MovieDao>, var context: Context):RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movie,parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MovieViewHolder ->{
                holder.bind(items[position],position)
            }
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }

    inner class MovieViewHolder(itemView: View):BaseViewHolder<MovieDao>(itemView){
        private val binding = RowMovieBinding.bind(itemView)
        override fun bind(item: MovieDao, position: Int) {

            Glide.with(context)
                .asBitmap()
                .load(Constants.URL_IMG +item.posterPath)
                .into(binding.imgMovie)

            binding.txtName.text =item.nameMovie
            binding.txtDescription.text = item.overview
            binding.txtDate.text = "Año: ${item.releaseDate}"
        }

    }
}