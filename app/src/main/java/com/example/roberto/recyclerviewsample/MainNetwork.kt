package com.example.roberto.recyclerviewsample

import android.content.Context
import com.example.roberto.recyclerviewsample.persistence.ChatData
import com.example.roberto.recyclerviewsample.utils.FAKE_JSON_RESPONSE_PATH
import com.example.roberto.recyclerviewsample.utils.FakeNetworkCall
import com.example.roberto.recyclerviewsample.utils.fakeNetworkLibrary

/**
 * Main network interface which will fetch a new welcome title for us
 */
interface MainNetwork {
    fun fetchChatData(): FakeNetworkCall<ChatData>
}

/**
 * Default implementation of MainNetwork.
 */
class MainNetworkImpl(context: Context) : MainNetwork {

    //TODO context reference?
    val objectArrayString = context.resources.openRawResource(FAKE_JSON_RESPONSE_PATH).bufferedReader().use { it.readText() }
    override fun fetchChatData() = fakeNetworkLibrary(objectArrayString)
}
