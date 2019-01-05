package com.example.roberto.recyclerviewsample

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.roberto.recyclerviewsample.persistence.Message
import kotlinx.android.synthetic.main.person_item.view.*

class MessageAdapter(val context: Context) :
    PagedListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onBindViewHolder(holderPerson: MessageViewHolder, position: Int) {
        val message = getItem(position)

        if (message == null) {
            holderPerson.clear()
        } else {
            holderPerson.bind(message)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.person_item,
                parent, false
            )
        )

        //TODO multiple view holder other person, me person, attachment item
        //TODO RECYCLING CHECKS
        //TODO STABLEIDS
        //TODO possibilità di cancellare ATTACHMENT e messaggi
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
}