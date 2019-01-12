package com.example.roberto.recyclerviewsample

import android.content.Context
import com.example.roberto.recyclerviewsample.persistence.models.ChatData
import com.example.roberto.recyclerviewsample.utils.FAKE_JSON_RESPONSE_PATH
import com.example.roberto.recyclerviewsample.utils.fakeNetworkLibrary

/**
 * Main network interface which will fetch all chat data
 */
interface MainNetwork {
    fun fetchChatData(): ChatData
}

/**
 * Default implementation of MainNetwork.
 */
class MainNetworkImpl(context: Context) : MainNetwork {

    private val objectArrayString =
        context.resources.openRawResource(FAKE_JSON_RESPONSE_PATH).bufferedReader().use { it.readText() }

    override fun fetchChatData() = fakeNetworkLibrary(objectArrayString)
}
