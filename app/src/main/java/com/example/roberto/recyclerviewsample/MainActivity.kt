package com.example.roberto.recyclerviewsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.roberto.recyclerviewsample.persistence.getDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = getDatabase(this) //todo spostare in application?
        val repository = ChatRepository(MainNetworkImpl(this), database.messageDao)

        viewModel = ViewModelProviders
            .of(this, MainViewModel.FACTORY(repository))
            .get(MainViewModel::class.java)

        val adapter = MessageAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter


        subscribeUI(adapter)

        viewModel.onMainViewLoaded()

    }

    private fun subscribeUI(adapter: MessageAdapter) {
        viewModel.messagesLiveData.observe(this, Observer { messages ->
            messages?.let {
                adapter.submitList(messages)
                Log.e("GHEE", "messages size " + it.size)
            }
        })

        // show the spinner when [MainViewModel.spinner] is true
        viewModel.spinner.observe(this, Observer { value ->
            value?.let { show ->
                // TODO spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

}
