package com.example.roberto.recyclerviewsample

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
object MainNetworkImpl : MainNetwork {
    override fun fetchChatData() = fakeNetworkLibrary(FAKE_JSON_RESPONSE_PATH)
}
