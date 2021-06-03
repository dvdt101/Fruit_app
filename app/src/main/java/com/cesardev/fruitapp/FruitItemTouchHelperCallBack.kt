package com.cesardev.fruitapp

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class FruitItemTouchHelperCallBack(private val fruitRecyclerAdapter: FruitAdapter,
                                    private val context: Context) : ItemTouchHelper.Callback(){

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

        val swipe = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(drag, swipe)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val initialPosition = viewHolder.adapterPosition
        val targetPosition = target.adapterPosition
        fruitRecyclerAdapter.swap(initialPosition, targetPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Deletar")
        builder.setMessage("Você realmente deseja deletar este item ?")
        builder.setPositiveButton("Sim"){dialog, it ->
            fruitRecyclerAdapter.remove(position)
            Toast.makeText(context,"Item removido", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Não"){dialog, it ->
            fruitRecyclerAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            Toast.makeText(context,"Ação cancelada", Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }
}