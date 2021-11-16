package com.torres.demo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.torres.demo.R
import com.torres.demo.databinding.RowPhotoBinding
import com.torres.demo.vo.BaseViewHolder

class PhotosAdapter(var items:ArrayList<String>,var context: Context):RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return PhotosViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PhotosViewHolder -> {
                holder.bind(items[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }

    fun addPhoto(photo:String){
        items.add(photo)
        notifyItemChanged(items.size-1)
    }

    inner class PhotosViewHolder(itemView:View):BaseViewHolder<String>(itemView){

        private val binding = RowPhotoBinding.bind(itemView)

        override fun bind(item: String, position: Int) {

            val uri: Uri = Uri.parse(item)
            Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(binding.imgPhoto)
        }
    }
}