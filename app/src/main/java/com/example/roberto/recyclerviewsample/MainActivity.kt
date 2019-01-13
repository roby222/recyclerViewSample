package com.example.roberto.recyclerviewsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.roberto.recyclerviewsample.persistence.ChatRepository
import com.example.roberto.recyclerviewsample.persistence.getDatabase
import com.example.roberto.recyclerviewsample.recycler.ChatElementAdapter
import com.example.roberto.recyclerviewsample.recycler.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = getDatabase(this)
        val repository = ChatRepository(MainNetworkImpl(this), database.chatElementDao)

        viewModel = ViewModelProviders
            .of(this, MainViewModel.FACTORY(repository))
            .get(MainViewModel::class.java)

        initRecycler()
    }

    private fun initRecycler() {
        val chatElementAdapter = ChatElementAdapter(this)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = chatElementAdapter
            itemAnimator = DefaultItemAnimator()
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback {
            //handling item delete
            viewModel.deleteItem(chatElementAdapter.getChatElementItem(it.adapterPosition))
        })
        itemTouchHelper.attachToRecyclerView(recyclerview)

        subscribeUI(chatElementAdapter)
    }

    private fun subscribeUI(adapter: ChatElementAdapter) {
        viewModel.chatElementsLiveData.observe(this, Observer { messages ->
            messages?.let {
                adapter.submitList(messages)
            }
        })
    }
}
