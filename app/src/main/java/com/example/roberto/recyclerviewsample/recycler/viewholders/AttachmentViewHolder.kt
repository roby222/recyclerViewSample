package com.example.roberto.recyclerviewsample.recycler.viewholders

import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.roberto.recyclerviewsample.R
import com.example.roberto.recyclerviewsample.persistence.models.ChatElement
import com.example.roberto.recyclerviewsample.utils.GlideApp
import kotlinx.android.synthetic.main.item_attachment.view.*


class AttachmentViewHolder(view: View) : CustomViewHolder(view) {
    private var tvAttachmentName: TextView = view.tvName
    private var ivAttachmentImage: ImageView = view.ivAttachment
    private var cardViewContainer: CardView = view.cardViewContainer
    private var userImageView: ImageView = view.imageViewAvatar
    private var cardViewAvatar: CardView = view.cardViewAvatar


    fun bind(chatElement: ChatElement) {
        tvAttachmentName.text = chatElement.attachment?.title

        GlideApp.with(ivAttachmentImage.context)
            .load(chatElement.attachment?.url)
            .placeholder(ivAttachmentImage.context.getDrawable(R.drawable.placeholder))
            .into(ivAttachmentImage)

        val params = cardViewContainer.layoutParams as ViewGroup.MarginLayoutParams

        if (chatElement.isOwnMessage) {
            params.marginEnd = cardViewContainer.context.resources.getDimension(R.dimen.own_padding).toInt()
            cardViewAvatar.visibility = View.INVISIBLE
            return
        }

        params.marginEnd = cardViewContainer.context.resources.getDimension(R.dimen.other_padding).toInt()
        cardViewAvatar.visibility = View.VISIBLE
        GlideApp
            .with(userImageView.context)
            .load(chatElement.userAvatar)
            .into(userImageView)

    }

    override fun recycle() {
        tvAttachmentName.text = null
        GlideApp.with(ivAttachmentImage.context).clear(ivAttachmentImage)
        GlideApp.with(userImageView.context).clear(userImageView)
    }
}