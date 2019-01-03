package com.example.roberto.recyclerviewsample.utils

import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.support.annotation.RawRes
import com.example.roberto.recyclerviewsample.persistence.ChatData
import com.google.gson.Gson
import java.util.concurrent.Executors


private const val ONE_SECOND = 1_000L

private val executor = Executors.newCachedThreadPool()

private val uiHandler = Handler(Looper.getMainLooper())


fun fakeNetworkLibrary(@RawRes jsonPath: Int): FakeNetworkCall<ChatData> {
    val result = FakeNetworkCall<ChatData>()

    // Launch the "network request" in a new thread to avoid blocking the calling thread
    executor.submit {
        Thread.sleep(ONE_SECOND) // pretend we actually made a network request by sleeping

        val objectArrayString = Resources.getSystem().openRawResource(jsonPath).bufferedReader().use { it.readText() }

        val gson = Gson()
        val chatData = gson.fromJson(objectArrayString, ChatData::class.java)
        result.onSuccess(chatData)

    }
    return result
}

/**
 * Fake Call for our network library used to observe results
 */
class FakeNetworkCall<T> {
    var result: FakeNetworkResult<T>? = null

    val listeners = mutableListOf<FakeNetworkListener<T>>()

    fun addOnResultListener(listener: (FakeNetworkResult<T>) -> Unit) {
        trySendResult(listener)
        listeners += listener
    }

    /**
     * The library will call this when a result is available
     */
    fun onSuccess(data: T) {
        result = FakeNetworkSuccess(data)
        sendResultToAllListeners()
    }

    /**
     * Broadcast the current result (success or error) to all registered listeners.
     */
    private fun sendResultToAllListeners() = listeners.map { trySendResult(it) }

    /**
     * Send the current result to a specific listener.
     *
     * If no result is set (null), this method will do nothing.
     */
    private fun trySendResult(listener: FakeNetworkListener<T>) {
        val thisResult = result
        thisResult?.let {
            uiHandler.post {
                listener(thisResult)
            }
        }
    }
}

/**
 * Network result class that represents both success and errors
 */
sealed class FakeNetworkResult<T>

/**
 * Passed to listener when the network request was successful
 *
 * @param data the result
 */
class FakeNetworkSuccess<T>(val data: T) : FakeNetworkResult<T>()

/**
 * Passed to listener when the network failed
 *
 * @param error the exception that caused this error
 */
class FakeNetworkError<T>(val error: Throwable) : FakeNetworkResult<T>()

/**
 * Listener "type" for observing a [FakeNetworkCall]
 */
typealias FakeNetworkListener<T> = (FakeNetworkResult<T>) -> Unit

/**
 * Throwable to use in fake network errors.
 */
class FakeNetworkException(message: String) : Throwable(message)
