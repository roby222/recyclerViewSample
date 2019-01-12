package com.example.roberto.recyclerviewsample.recycler.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun recycle()
}