package com.example.roberto.recyclerviewsample

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.roberto.recyclerviewsample.viewholders.CustomViewHolder

class SwipeToDeleteCallback(private val mAdapter: MessageAdapter,private val onDelete: (CustomViewHolder) -> Unit) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        viewHolder1: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        viewHolder?.let {
            val listItemViewHolder = viewHolder as CustomViewHolder
            onDelete(listItemViewHolder)
        }
    }
}
