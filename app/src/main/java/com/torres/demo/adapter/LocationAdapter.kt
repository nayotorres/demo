package com.torres.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.torres.demo.R
import com.torres.demo.data.model.LocationEntity
import com.torres.demo.databinding.RowLocationBinding
import com.torres.demo.utilities.ClickAdapter
import com.torres.demo.vo.BaseViewHolder

class LocationAdapter(var items:ArrayList<LocationEntity>,var context: Context, var call:ClickAdapter):RecyclerView.Adapter<BaseViewHolder<*>>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return LocationViewHolder(LayoutInflater.from(context).inflate(R.layout.row_location,parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is LocationViewHolder ->{
                holder.bind(items[position],position)
            }
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }

    inner class LocationViewHolder(itemViw:View):BaseViewHolder<LocationEntity>(itemViw){
        private val binding = RowLocationBinding.bind(itemView)
        override fun bind(item: LocationEntity, position: Int) {
            binding.txtLocation.text  =item.name

            itemView.setOnClickListener {
                call.result(item,position)
            }
        }
    }
}