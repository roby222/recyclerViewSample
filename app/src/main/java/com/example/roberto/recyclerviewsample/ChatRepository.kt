package com.example.roberto.recyclerviewsample

import android.arch.paging.DataSource
import com.example.roberto.recyclerviewsample.persistence.dao.MessageDao
import com.example.roberto.recyclerviewsample.persistence.models.Attachment
import com.example.roberto.recyclerviewsample.persistence.models.ChatData
import com.example.roberto.recyclerviewsample.persistence.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ChatRepository(private val network: MainNetwork, private val messagesDao: MessageDao) {

    val paginatedMessages: DataSource.Factory<Int, Message> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        messagesDao.loadMessagesPaginated()
    }

    suspend fun deleteMessage(message: Message) {
        withContext(Dispatchers.IO) {
            messagesDao.delete(message)
        }
    }

    suspend fun refreshChatBox() {
        withContext(Dispatchers.IO) {
            if (messagesDao.getMessageNumber() == 0) {
                val chatData = network.fetchChatData()
                messagesDao.insertMessagesCustom(handleData(chatData))
            }
        }
    }

    private fun handleData(chatData: ChatData): MutableList<Message> {
        val finalMessages: MutableList<Message> = mutableListOf()

        val messages = chatData.messages
        var indexLong = -1L

        //TODO damned room @embedded
        //https://android.jlelse.eu/setting-android-room-in-real-project-58a77469737c
        //https://commonsware.com/AndroidArch/previews/room-and-custom-types
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
        return finalMessages
    }
}