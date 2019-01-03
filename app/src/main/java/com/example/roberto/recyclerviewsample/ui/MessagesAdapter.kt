package com.example.roberto.recyclerviewsample.ui

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.roberto.recyclerviewsample.R
import com.example.roberto.recyclerviewsample.ui.models.Message
import kotlin.properties.Delegates
import kotlinx.android.extensions.LayoutContainer

//TODO
class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.ViewHolder>(){

    var items: List<Message> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.view_message_item)) //TODO check adapter

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bind(message: Message) {
            with(message) {
              /*  */
               // TODO
            }
        }
    }
}