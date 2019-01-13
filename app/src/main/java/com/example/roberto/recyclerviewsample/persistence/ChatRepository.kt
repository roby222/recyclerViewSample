package com.example.roberto.recyclerviewsample.persistence

import android.arch.paging.DataSource
import com.example.roberto.recyclerviewsample.MainNetwork
import com.example.roberto.recyclerviewsample.persistence.dao.ChatElementDao
import com.example.roberto.recyclerviewsample.persistence.models.ChatElement
import com.example.roberto.recyclerviewsample.persistence.models.dto.AttachmentDTO
import com.example.roberto.recyclerviewsample.persistence.models.dto.ChatDataDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ChatRepository(private val network: MainNetwork, private val chatElementDao: ChatElementDao) {

    val paginatedChatElements: DataSource.Factory<Int, ChatElement> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        chatElementDao.load()
    }

    suspend fun deleteChatElement(chatElement: ChatElement) {
        withContext(Dispatchers.IO) {
            chatElementDao.delete(chatElement)
        }
    }

    fun loadChatElements() {
        val chatData = network.fetchChatData()
        chatElementDao.insert(handleData(chatData))
    }

    private fun handleData(chatData: ChatDataDTO): MutableList<ChatElement> {
        val chatElementList: MutableList<ChatElement> = mutableListOf()

        val messages = chatData.messages
        var indexLong = -1L

        //TODO damned room @embedded
        /*  Tricky part:
            I handle all local data in a "custom ChatElement object" to be easy for the recycler adapter use.
        */
        messages.onEach { messageDTO ->
            val user = chatData.users.find { it.id == messageDTO.userId }

            // original message
            chatElementList.add(
                ChatElement(
                    id = ++indexLong,
                    messageId = messageDTO.id,
                    userId = messageDTO.userId,
                    content = messageDTO.content,
                    userName = user!!.name,
                    userAvatar = user.avatarId,
                    _attachment = null,
                    isAnAttachment = false
                )
            )

            //only italian spaghetti code: a fake message for each attachment
            val attachmentList: List<AttachmentDTO>? = messageDTO.attachments
            attachmentList?.onEach { attachment ->
                chatElementList.add(
                    ChatElement(
                        id = ++indexLong,
                        messageId = messageDTO.id,
                        userId = messageDTO.userId,
                        content = messageDTO.content,
                        userName = user.name,
                        userAvatar = user.avatarId,
                        _attachment = attachment,
                        isAnAttachment = true
                    )
                )
            }
        }
        return chatElementList
    }
}