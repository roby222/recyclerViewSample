package com.example.roberto.recyclerviewsample.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.roberto.recyclerviewsample.persistence.models.Message
import kotlinx.android.synthetic.main.item_attachment.view.*


class AttachmentViewHolder(view: View) : CustomViewHolder(view) { //TODO ui
    var tvAttachmentName: TextView = view.atttvname
    var ivAttachmentImage: ImageView = view.attimageView


    fun bind(message: Message) {
        tvAttachmentName.text = message.attachment?.title
        Glide.with(ivAttachmentImage.context)
            .load(message.attachment?.url)
            .into(ivAttachmentImage)
    }

    fun clear() { //TODO
        tvAttachmentName.text = null
        //TODO check
    }

    override fun clearAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}