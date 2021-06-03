package com.cesardev.fruitapp

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
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
        builder.setTitle(context.getString(R.string.dialog_title))
        builder.setMessage(context.getString(R.string.dialog_message))
        builder.setPositiveButton(context.getString(R.string.yes_option)){dialog, it ->
            fruitRecyclerAdapter.remove(position)
            Toast.makeText(context,context.getString(R.string.removed_item), Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(context.getString(R.string.no_option)){dialog, it ->
            fruitRecyclerAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            Toast.makeText(context, context.getString(R.string.action_canceled), Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }
}