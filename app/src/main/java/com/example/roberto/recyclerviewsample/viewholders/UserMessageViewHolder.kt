package com.example.roberto.recyclerviewsample.viewholders

import android.support.v7.widget.CardView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.TextView
import com.example.roberto.recyclerviewsample.R
import com.example.roberto.recyclerviewsample.persistence.models.Message
import kotlinx.android.synthetic.main.item_message.view.*


class UserMessageViewHolder(view: View) : CustomViewHolder(view) { //TODO UI
    var imageView: ImageView = view.imageView
    var tvName: TextView = view.tvname
    var tvMessage: TextView = view.tvmessage
    var cardViewMessage: CardView = view.cardView
    var cardViewAvatar: CardView = view.cardViewAvatar

    fun bind(message: Message) {
        tvMessage.text = message.content
        tvName.text = tvName.context.getString(R.string.own_message_title)
        tvName.gravity = Gravity.RIGHT
        cardViewAvatar.visibility = View.INVISIBLE

        val params = cardViewMessage.layoutParams as MarginLayoutParams
        params.marginEnd = params.marginEnd / 2

    }

    fun clear() { //TODO
        tvName.text = null
    }

    override fun clearAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}