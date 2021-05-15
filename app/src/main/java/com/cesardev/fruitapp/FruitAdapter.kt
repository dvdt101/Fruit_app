package com.cesardev.fruitapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessControlContext


class FruitAdapter(private val fruitList: List<Fruit>) : RecyclerView.Adapter<FruitAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.image_view)
        val titleView : TextView = itemView.findViewById(R.id.titleView)
        val descriptionView : TextView = itemView.findViewById(R.id.descriptionView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fruit_card_view,parent,false)
        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem = fruitList[position]
        holder.imageView.setImageResource(currentItem.image)
        holder.titleView.text = currentItem.name
        holder.descriptionView.text = currentItem.description
    }

    override fun getItemCount() = fruitList.size

}
