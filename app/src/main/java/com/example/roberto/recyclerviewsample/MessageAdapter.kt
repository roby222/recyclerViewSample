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
import com.example.roberto.recyclerviewsample.persistence.models.Message
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_attachment.view.*
import kotlinx.android.synthetic.main.item_message.view.*


class MessageAdapter(val context: Context) :
    PagedListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    private val MESSAGE = 0
    private val ATTACHMENT = 1
    private val USER_MESSAGE = 2

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (viewHolder.itemViewType) {
            MESSAGE -> {
                val messageViewHolder = viewHolder as MessageViewHolder
                messageViewHolder.bind(message!!)
            }
            USER_MESSAGE -> {
                val userMessageViewHolder = viewHolder as UserMessageViewHolder
                userMessageViewHolder.bind(message!!)
            }
            ATTACHMENT -> {
                val attachmentViewHolder = viewHolder as AttachmentViewHolder
                attachmentViewHolder.bind(message!!)
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
        if (getItem(position)!!.userId == 1L) {
            return USER_MESSAGE
        }
        return MESSAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var recyclerViewHolder: RecyclerView.ViewHolder
        when (viewType) {
            ATTACHMENT -> {
                recyclerViewHolder = AttachmentViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_attachment, parent, false)
                )
            }
            MESSAGE -> {
                recyclerViewHolder = MessageViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_message,
                        parent, false
                    )
                )
            }
            USER_MESSAGE -> {
                recyclerViewHolder = MessageViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_user_message,
                        parent, false
                    )
                )
            }
        }
        return recyclerViewHolder
    }

    //TODO possibilità di cancellare ATTACHMENT e messaggi
    //TODO RECYCLING CHECKS e clear


    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) { //TODO UI
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

    class UserMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) { //TODO UI
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

    class AttachmentViewHolder(view: View) : RecyclerView.ViewHolder(view) { //TODO ui
        var tvAttachmentName: TextView = view.atttvname
        var ivAttachmentImage: ImageView = view.attimageView

        fun bind(message: Message) {
//TODO if userId = 1 => change alignment
            tvAttachmentName.text = message.attachment?.title
            Glide.with(ivAttachmentImage.context)
                .load(message.attachment?.url)
                .into(ivAttachmentImage)
        }

        fun clear() {
            tvAttachmentName.text = null
            ivAttachmentImage.recyclerview //TODO check
        }
    }
}