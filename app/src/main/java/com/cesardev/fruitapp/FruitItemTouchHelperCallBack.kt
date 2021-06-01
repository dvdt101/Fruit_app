package com.cesardev.fruitapp

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class FruitItemTouchHelperCallBack(private val fruitRecyclerAdapter: FruitAdapter) : ItemTouchHelper.Callback(){

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

        val swipe = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(swipe, drag)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val initialPosition = viewHolder.adapterPosition
        val targetPosition = target.adapterPosition
        fruitRecyclerAdapter.swap(initialPosition, targetPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        fruitRecyclerAdapter.remove(position)
    }
}