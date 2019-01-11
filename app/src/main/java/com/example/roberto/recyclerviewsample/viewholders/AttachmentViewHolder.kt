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


class AttachmentViewHolder(view: View) : CustomViewHolder(view) { 
    var tvAttachmentName: TextView = view.atttvname
    var ivAttachmentImage: ImageView = view.attimageView
    var cardViewContainer : CardView = view.cardView


    fun bind(message: Message) {
        tvAttachmentName.text = message.attachment?.title
        Glide.with(ivAttachmentImage.context)
            .load(message.attachment?.url)
            .into(ivAttachmentImage)

        val params = cardViewContainer.layoutParams as ViewGroup.MarginLayoutParams

        if(message.isOwnMessage){
            params.marginEnd = 20.px
        }
        else{
            params.marginEnd = 40.px

        }
    }

    fun clear() { //TODO
        tvAttachmentName.text = null
        //TODO check
    }

    override fun clearAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}