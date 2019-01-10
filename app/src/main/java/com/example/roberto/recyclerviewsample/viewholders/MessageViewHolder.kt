package com.example.roberto.recyclerviewsample.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.roberto.recyclerviewsample.persistence.models.Message
import kotlinx.android.synthetic.main.item_message.view.*

class MessageViewHolder(view: View) : CustomViewHolder(view) { //TODO UI
    var imageView: ImageView = view.imageView
    var tvName: TextView = view.tvname
    var tvMessage: TextView = view.tvmessage

    fun bind(message: Message) {
        tvMessage.text = message.content
        tvName.text = message.userName
        Glide
            .with(imageView.context)
            .load(message.userAvatar)
            .into(imageView)
    }

    fun clear() { //TODO
        tvName.text = null
    }

    override fun clearAnimation() { //clean for animation during fast scroll - create generic view holder
        TODO("not implemented")  //

        // mRootLayout.clearAnimation()
    }
}