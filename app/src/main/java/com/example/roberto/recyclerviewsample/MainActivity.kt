package com.example.roberto.recyclerviewsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.example.roberto.recyclerviewsample.persistence.ChatRepository
import com.example.roberto.recyclerviewsample.persistence.getDatabase
import com.example.roberto.recyclerviewsample.recycler.MessageAdapter
import com.example.roberto.recyclerviewsample.recycler.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = getDatabase(this)
        val repository = ChatRepository(
            MainNetworkImpl(this),
            database.messageDao
        )

        viewModel = ViewModelProviders
            .of(this, MainViewModel.FACTORY(repository))
            .get(MainViewModel::class.java)

        val messageAdapter = MessageAdapter(this)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = messageAdapter
            itemAnimator = DefaultItemAnimator()
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback {
            viewModel.deleteItem(messageAdapter.getMessageItem(it.adapterPosition))
        })
        itemTouchHelper.attachToRecyclerView(recyclerview)

        subscribeUI(messageAdapter)
        viewModel.onMainViewLoaded()
    }

    private fun subscribeUI(adapter: MessageAdapter) {
        viewModel.messagesLiveData.observe(this, Observer { messages ->
            messages?.let {
                adapter.submitList(messages)
            }
        })
    }
}
