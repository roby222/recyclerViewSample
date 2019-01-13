package com.example.roberto.recyclerviewsample.recycler

import android.support.v7.util.DiffUtil
import com.example.roberto.recyclerviewsample.persistence.models.ChatElement

class ChatElementDiffCallback : DiffUtil.ItemCallback<ChatElement>() {

    override fun areContentsTheSame(oldItem: ChatElement, newItem: ChatElement): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ChatElement, newItem: ChatElement): Boolean {
        return oldItem.id == newItem.id
    }
}