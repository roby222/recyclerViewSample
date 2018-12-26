package com.example.roberto.data

import com.example.roberto.domain.Message

class MessagesRepository(

    private val messagePersistenceSource: MessagePersistenceSource,
    private val messageRemoteSource: MessageRemoteSource
) {
    fun getSavedMessages(): List<Message> = messagePersistenceSource.getPersistedMessages()

    fun requestMessages(): List<Message> {
        val messages = messageRemoteSource.getRemoteMessages()
        messagePersistenceSource.saveMessages(messages)
        return getSavedMessages()
    }
}

interface MessagePersistenceSource {
    fun getPersistedMessages(): List<Message>
    fun saveMessages(messages: List<Message>)
}

interface MessageRemoteSource {
    fun getRemoteMessages(): List<Message>
}

