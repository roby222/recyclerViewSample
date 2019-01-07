package com.example.roberto.recyclerviewsample

import android.arch.paging.DataSource
import com.example.roberto.recyclerviewsample.persistence.dao.MessageDao
import com.example.roberto.recyclerviewsample.persistence.models.Attachment
import com.example.roberto.recyclerviewsample.persistence.models.Message
import com.example.roberto.recyclerviewsample.utils.FakeNetworkCall
import com.example.roberto.recyclerviewsample.utils.FakeNetworkError
import com.example.roberto.recyclerviewsample.utils.FakeNetworkException
import com.example.roberto.recyclerviewsample.utils.FakeNetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class ChatRepository(val network: MainNetwork, val messagesDao: MessageDao) {


    val paginatedMessages: DataSource.Factory<Int, Message> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        messagesDao.loadMessagesPaginated()
    }

    suspend fun refreshChatBox() {
        withContext(Dispatchers.IO) {
            try {
                if (messagesDao.getMessageNumber() == 0) { //TODO check query
                    val chatData = network.fetchChatData().await()
                    val messages = chatData.messages
                    var indexLong = -1L

                    //TODO damned room @embedded
                    //https://android.jlelse.eu/setting-android-room-in-real-project-58a77469737c
                    //https://commonsware.com/AndroidArch/previews/room-and-custom-types
                    val finalMessages: MutableList<Message> = mutableListOf()
                    messages.onEach { messageDTO ->
                        val user = chatData.users.find { it.id == messageDTO.userId }
                        finalMessages.add(
                            Message(
                                // UUID.randomUUID().toString(), //FAKE id
                                ++indexLong,
                                messageDTO.id,
                                messageDTO.userId,
                                messageDTO.content,
                                user!!.name,
                                user.avatarId,
                                null,
                                false
                            )
                        ) // original message
                        //only italian spaghetti code: add fake message
                        val attachmentList: List<Attachment>? = messageDTO.attachments
                        attachmentList?.onEach { attachment ->
                            finalMessages.add(
                                Message(
                                    ++indexLong,
                                    messageDTO.id,
                                    messageDTO.userId,
                                    messageDTO.content,
                                    user.name,
                                    user.avatarId,
                                    attachment,
                                    true
                                )
                            )
                        }
                    }
                    messagesDao.insertMessagesCustom(finalMessages)
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

