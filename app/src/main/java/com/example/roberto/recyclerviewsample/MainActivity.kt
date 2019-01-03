package com.example.roberto.recyclerviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.roberto.recyclerviewsample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INIT UI della recycler TODO

        // Get MainViewModel by passing a database to the factory
        /*
        TODO val database = getDatabase(this)
        TODO val repository = TitleRepository(MainNetworkImpl, database.titleDao)
        TODO val viewModel = ViewModelProviders
             .of(this, MainViewModel.FACTORY(repository))
             .get(MainViewModel::class.java)
        */

        // update the recyclerView when the [MainViewModel.title] changes TODO
        /*  viewModel.title.observe(this, Observer { value ->
              value?.let {
                  title.text = it
              }
          })

          // show the spinner when [MainViewModel.spinner] is true TODO
          viewModel.spinner.observe(this, Observer { value ->
              value?.let { show ->
                  spinner.visibility = if (show) View.VISIBLE else View.GONE
              }
          })
          */
    }

}
