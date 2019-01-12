package com.example.roberto.recyclerviewsample.recycler.viewholders

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.roberto.recyclerviewsample.R
import com.example.roberto.recyclerviewsample.persistence.models.Message
import com.example.roberto.recyclerviewsample.utils.GlideApp
import kotlinx.android.synthetic.main.item_message.view.*

class MessageViewHolder(view: View) : CustomViewHolder(view) {
    private var imageView: ImageView = view.imageViewAvatar
    private var tvName: TextView = view.tvname
    private var tvMessage: TextView = view.tvmessage
    private var cardViewMessage: CardView = view.cardViewContainer
    private var cardViewAvatar: CardView = view.cardViewAvatar

    fun bind(message: Message) {
        tvMessage.text = message.content

        val params = cardViewMessage.layoutParams as ViewGroup.MarginLayoutParams
        if (message.isOwnMessage) {
            tvName.text = tvName.context.getString(R.string.own_message_title)
            tvName.gravity = Gravity.END
            cardViewAvatar.visibility = View.INVISIBLE
            params.marginEnd = cardViewMessage.context.resources.getDimension(R.dimen.own_padding).toInt()
            cardViewMessage.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(cardViewMessage.context, R.color.colorGrey))
            return
        }

        tvName.text = message.userName
        tvName.gravity = Gravity.START
        cardViewAvatar.visibility = View.VISIBLE
        cardViewMessage.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(cardViewMessage.context, R.color.colorWhite))
        params.marginEnd = cardViewMessage.context.resources.getDimension(R.dimen.other_padding).toInt()

        GlideApp
            .with(imageView.context)
            .load(message.userAvatar)
            .into(imageView)
    }

    override fun recycle() {
        tvName.text = null
        tvMessage.text = null
        GlideApp.with(imageView.context).clear(imageView)
    }
}