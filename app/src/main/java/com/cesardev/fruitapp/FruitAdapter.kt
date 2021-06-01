package com.cesardev.fruitapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessControlContext
import java.util.*


class FruitAdapter(private val fruitList: MutableList<Fruit>,
                   private val listenner: OnItemClickListenner,
                   private val context: Context
) : RecyclerView.Adapter<FruitAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val imageView : ImageView = itemView.findViewById(R.id.image_view)
        val titleView : TextView = itemView.findViewById(R.id.titleView)
        val descriptionView : TextView = itemView.findViewById(R.id.descriptionView)

        //Seta onClickListener para os items
        init {
            itemView.setOnClickListener(this)
        }
        //Sobescreve função onClick na View.OnClickListener
        override fun onClick(v: View?) {
            val position = getBindingAdapterPosition()

            if(position != RecyclerView.NO_POSITION)
                listenner.onItemClick(position)
        }

    }

    interface OnItemClickListenner{
        fun onItemClick(position: Int)
    }

    //onde o layout que será usado no RecyclerView é inflado.
    // O próprio adapter já usa View Holder para reusar o objetos criados.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fruit_card_view,parent,false)
        return  ViewHolder(itemView)
    }

    //preenche o objeto View Holder com as informações.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem = fruitList[position]

        if(currentItem.dummyImage != 0){
            holder.imageView.setImageResource(currentItem.dummyImage)
        }else if(currentItem.BitmapImage != null){
            holder.imageView.setImageBitmap(currentItem.BitmapImage)
        }else{
            holder.imageView.setImageResource(R.drawable.ic_android)
        }

        holder.titleView.text = currentItem.name
        holder.descriptionView.text = currentItem.benefits
    }

    override fun getItemCount() = fruitList.size


    fun swap(initialPosition: Int, targetPosition: Int) {
        Collections.swap(fruitList, initialPosition, targetPosition)
        notifyItemMoved(initialPosition, targetPosition)
    }

    fun remove(position: Int) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Deletar")
        builder.setMessage("Você realmente deseja deletar este item ?")
        builder.setPositiveButton("Sim"){dialog, it ->
            fruitList.removeAt(position)
            notifyItemRemoved(position)
        }
        builder.setNegativeButton("Não"){dialog, it ->
            Toast.makeText(context,"Ação cancelada", Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }

}
