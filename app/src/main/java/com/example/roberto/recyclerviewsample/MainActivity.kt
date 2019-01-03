package com.example.roberto.recyclerviewsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.roberto.recyclerviewsample.persistence.getDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //TODO LAYOUT

        //INIT UI della recycler TODO

        val database = getDatabase(this) //todo spostare in application?
        val repository = ChatRepository(MainNetworkImpl(this), database.messageDao, database.userDao)
        val viewModel = ViewModelProviders
            .of(this, MainViewModel.FACTORY(repository))
            .get(MainViewModel::class.java)



        // update the title when the [MainViewModel.title] changes
        viewModel.messages.observe(this, Observer { value ->
            value?.let {

                Log.e("GHEE","messages size "+it.size) //TODO update adapter
            }
        })


          // show the spinner when [MainViewModel.spinner] is true
          viewModel.spinner.observe(this, Observer { value ->
              value?.let { show ->
                  // TODO spinner.visibility = if (show) View.VISIBLE else View.GONE
              }
          })


        viewModel.onMainViewLoaded() //TODO is the better place?
    }



}
