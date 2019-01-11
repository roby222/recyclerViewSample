package com.example.roberto.recyclerviewsample.viewholders

import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.roberto.recyclerviewsample.persistence.models.Message
import com.example.roberto.recyclerviewsample.utils.px
import kotlinx.android.synthetic.main.item_attachment.view.*



class OwnAttachmentViewHolder(view: View) : CustomViewHolder(view) { //TODO ui
    var tvAttachmentName: TextView = view.atttvname
    var ivAttachmentImage: ImageView = view.attimageView
    var cardViewMessage: CardView = view.cardView


    fun bind(message: Message) {
        tvAttachmentName.text = message.attachment?.title
        Glide.with(ivAttachmentImage.context)
            .load(message.attachment?.url)
            .into(ivAttachmentImage)

        val params = cardViewMessage.layoutParams as ViewGroup.MarginLayoutParams
        params.marginEnd = 20.px
    }

    fun clear() { //TODO
        tvAttachmentName.text = null
       //TODO check
    }

    override fun clearAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}