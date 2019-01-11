package com.example.roberto.recyclerviewsample

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roberto.recyclerviewsample.persistence.models.Message
import com.example.roberto.recyclerviewsample.viewholders.AttachmentViewHolder
import com.example.roberto.recyclerviewsample.viewholders.MessageViewHolder


class MessageAdapter(val context: Context) :
    PagedListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    private val MESSAGE = 0
    private val ATTACHMENT = 1

    private var lastPosition = -1


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
        //TODO setAnimation(viewHolder.itemView, position)
    }

    //TODO check animation https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-1-list-75a874a5d213
    /*private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        (holder as CustomViewHolder).clearAnimation()
    }*/

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

    //TODO possibilità di cancellare ATTACHMENT e messaggi
/*    private fun setRecyclerViewItemTouchListener() {

        //1
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
                photosList.removeAt(position)
                recyclerView.adapter.notifyItemRemoved(position)
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }*/


    //TODO RECYCLING CHECKS e clear


}