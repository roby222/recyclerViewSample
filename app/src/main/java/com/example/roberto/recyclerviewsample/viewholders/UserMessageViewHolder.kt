package com.example.roberto.recyclerviewsample.viewholders

import android.view.View
import android.widget.TextView
import com.example.roberto.recyclerviewsample.persistence.models.Message
import kotlinx.android.synthetic.main.item_message.view.*

class UserMessageViewHolder(view: View) : CustomViewHolder(view) { //TODO UI
    var tvName: TextView = view.tvname
    var tvMessage: TextView = view.tvmessage

    fun bind(message: Message) {
        tvMessage.text = message.content
        tvName.text = message.userId.toString()
    }

    fun clear() {
        tvName.text = null
    }

    override fun clearAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}