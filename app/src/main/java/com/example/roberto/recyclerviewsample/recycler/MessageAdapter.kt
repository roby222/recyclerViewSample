package com.example.roberto.recyclerviewsample.recycler

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roberto.recyclerviewsample.R
import com.example.roberto.recyclerviewsample.persistence.models.Message
import com.example.roberto.recyclerviewsample.recycler.viewholders.AttachmentViewHolder
import com.example.roberto.recyclerviewsample.recycler.viewholders.CustomViewHolder
import com.example.roberto.recyclerviewsample.recycler.viewholders.MessageViewHolder

private const val MESSAGE = 0
private const val ATTACHMENT = 1

class MessageAdapter(private val context: Context) :
    PagedListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

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
        if (getItem(position)!!.isAnAttachment) {
            return ATTACHMENT
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
        }
        return recyclerViewHolder
    }

    fun getMessageItem(position: Int): Message {
        return getItem(position)!!
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as CustomViewHolder).recycle()
    }
}