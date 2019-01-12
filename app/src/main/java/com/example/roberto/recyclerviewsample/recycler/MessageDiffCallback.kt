package com.example.roberto.recyclerviewsample.recycler

import android.support.v7.util.DiffUtil
import com.example.roberto.recyclerviewsample.persistence.models.Message

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }
}