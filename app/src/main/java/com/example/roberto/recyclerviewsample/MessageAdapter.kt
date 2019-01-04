package com.example.roberto.recyclerviewsample

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.roberto.recyclerviewsample.persistence.Message
import com.example.roberto.recyclerviewsample.persistence.User
import kotlinx.android.synthetic.main.person_item.view.*

class MessageAdapter(val context: Context) : PagedListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    private lateinit var userList  : List<User>

    fun setUsers(users :  List<User>){
        userList = users
    }

    override fun onBindViewHolder(holderPerson: MessageViewHolder, position: Int) {
        val message = getItem(position)

        if (message == null) {
            holderPerson.clear()
        } else {
            message.userName = userList.find{it.id == message.userId}?.name ?: ""
            holderPerson.bind(message)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.person_item,
            parent, false))

        //TODO multiple view holder other person, me person, attachment item
    }


    class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.tvname
        var tvMessage: TextView = view.tvmessage

        fun bind(message: Message) {
            tvMessage.text = message.content
            tvName.text = message.userName
        }

        fun clear() {
            tvName.text = null
        }

    }
}