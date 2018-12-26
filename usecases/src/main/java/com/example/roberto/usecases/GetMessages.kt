package com.example.roberto.usecases

import com.example.roberto.data.MessagesRepository
import com.example.roberto.domain.Message

class GetMessages(private val messagesRepository: MessagesRepository) {

    operator fun invoke(): List<Message> = messagesRepository.getSavedMessages()

}
