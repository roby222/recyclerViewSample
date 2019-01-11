package com.example.roberto.recyclerviewsample.viewholders

import android.support.v7.widget.CardView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.roberto.recyclerviewsample.R
import com.example.roberto.recyclerviewsample.persistence.models.Message
import com.example.roberto.recyclerviewsample.utils.px
import kotlinx.android.synthetic.main.item_message.view.*

class MessageViewHolder(view: View) : CustomViewHolder(view) {
    var imageView: ImageView = view.imageView
    var tvName: TextView = view.tvname
    var tvMessage: TextView = view.tvmessage
    var cardViewMessage: CardView = view.cardView
    var cardViewAvatar: CardView = view.cardViewAvatar

    fun bind(message: Message) {
        tvMessage.text = message.content

        val params = cardViewMessage.layoutParams as ViewGroup.MarginLayoutParams
        if (message.isOwnMessage) {
            tvName.text = tvName.context.getString(R.string.own_message_title)
            tvName.gravity = Gravity.END
            cardViewAvatar.visibility = View.INVISIBLE
            params.marginEnd = 20.px
            return
        }

        tvName.text = message.userName
        tvName.gravity = Gravity.START
        cardViewAvatar.visibility = View.VISIBLE
        params.marginEnd = 40.px

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