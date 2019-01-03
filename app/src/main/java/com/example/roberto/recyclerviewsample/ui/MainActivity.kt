package com.example.roberto.recyclerviewsample.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.roberto.recyclerviewsample.R
import com.example.roberto.recyclerviewsample.ui.models.Message
import com.example.roberto.recyclerviewsample.ui.models.User

class MainActivity : AppCompatActivity(), MainPresenter.View {

  /*  private val locationsAdapter = LocationsAdapter()
    private val presenter: MainPresenter

    init {
        // This would be done by a dependency injector in a complex App
        //
        val persistence = InMemoryLocationPersistenceSource()
        val deviceLocation = FakeLocationSource()
        val locationsRepository = LocationsRepository(persistence, deviceLocation)
        presenter = MainPresenter(
            this,
            GetLocations(locationsRepository),
            RequestNewLocation(locationsRepository)
        )
    } */
//TODO init passare a MVVM + room?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INIT della recycler TODO
        //presenter create
    }

    override fun onDestroy() {
        //presenter.onDestroy TODO
        super.onDestroy()
    }

    override fun renderMessages(messages: List<Message>, users: List<User>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
