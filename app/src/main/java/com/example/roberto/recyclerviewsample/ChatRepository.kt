package com.example.roberto.recyclerviewsample

import android.arch.paging.DataSource
import com.example.roberto.recyclerviewsample.persistence.Message
import com.example.roberto.recyclerviewsample.persistence.MessageDao
import com.example.roberto.recyclerviewsample.utils.FakeNetworkCall
import com.example.roberto.recyclerviewsample.utils.FakeNetworkError
import com.example.roberto.recyclerviewsample.utils.FakeNetworkException
import com.example.roberto.recyclerviewsample.utils.FakeNetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class ChatRepository(val network: MainNetwork, val messagesDao: MessageDao) {


    val paginatedMessages: DataSource.Factory<Int, Message> by lazy<DataSource.Factory<Int, Message>>(
        LazyThreadSafetyMode.NONE
    ) {
        messagesDao.loadMessagesPaginated()
    }

    suspend fun refreshChatBox() {
        withContext(Dispatchers.IO) {
            try {
                if (messagesDao.getMessageNumber() == 0) { //TODO check query
                    val chatData = network.fetchChatData().await()
                    var messages = chatData.messages

                    //TODO damned room @embedded
                    //https://android.jlelse.eu/setting-android-room-in-real-project-58a77469737c
                    //https://commonsware.com/AndroidArch/previews/room-and-custom-types
                    messages.onEach { message ->
                        val user = chatData.users.find { it.id == message.userId }
                        message.userName = user!!.name
                        message.userAvatar = user.avatarId
                    }
                    messagesDao.insertMessagesCustom(messages)
                }
            } catch (error: FakeNetworkException) {
                throw ChatBoxRefreshError(error)
            }
        }
    }

}

class ChatBoxRefreshError(cause: Throwable) : Throwable(cause.message, cause)

suspend fun <T> FakeNetworkCall<T>.await(): T {
    return kotlin.coroutines.suspendCoroutine { continuation ->
        addOnResultListener { result ->
            when (result) {
                is FakeNetworkSuccess<T> -> continuation.resume(result.data)
                is FakeNetworkError -> continuation.resumeWithException(result.error)
            }
        }
    }
}

