package com.example.roberto.recyclerviewsample

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.roberto.recyclerviewsample.persistence.Message
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_attachment.view.*
import kotlinx.android.synthetic.main.item_message.view.*


class MessageAdapter(val context: Context) :
    PagedListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    private val MESSAGE = 0
    private val ATTACHMENT = 1
    private val USER_MESSAGE = 2

    init {
        setHasStableIds(true);
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)

        when (viewHolder.getItemViewType()) {
            MESSAGE -> {
                val messageViewHolder = viewHolder as MessageViewHolder
                messageViewHolder.bind(message!!)
            }
            ATTACHMENT -> {
                val attachmentViewHolder = viewHolder as AttachmentViewHolder
                attachmentViewHolder.bind(message!!, context)
            }
        }


    }

    override fun getItemId(position: Int): Long {
        return getItem(position)!!.id
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) == null) {
            return -1
        }
        if (getItem(position)!!.isAnAttachment) {
            return ATTACHMENT
        }
        return MESSAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ATTACHMENT) {
            return AttachmentViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_attachment, parent, false
                )
            )
        }
        return MessageViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_message,
                parent, false
            )
        )


        //TODO RECYCLING CHECKS
        //TODO possibilità di cancellare ATTACHMENT e messaggi
        //TODO layout per userID = 1
    }


    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.tvname
        var tvMessage: TextView = view.tvmessage

        fun bind(message: Message) {

            tvMessage.text = message.content
            tvName.text = message.userId.toString()

        }

        fun clear() {
            tvName.text = null
        }

    }

    class AttachmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvAttachmentName: TextView = view.atttvname
        var ivAttachmentImage: ImageView = view.attimageView

        fun bind(message: Message, context: Context) {

            tvAttachmentName.text = message.attachmentSpaghetti?.title
            Glide.with(ivAttachmentImage.context)
                .load(message.attachmentSpaghetti?.url)
                .into(ivAttachmentImage)
        }

        fun clear() {
            tvAttachmentName.text = null
            ivAttachmentImage.recyclerview //TODO check
        }

    }
}