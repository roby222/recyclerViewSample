package com.example.roberto.recyclerviewsample.ui.models

import com.example.roberto.domain.Attachment
import java.util.ArrayList
import com.example.roberto.domain.Message as DomainMessage

data class Message(val id: Long, val userId: Long, val content: String, val attachment: ArrayList<Attachment>)

fun DomainMessage.toPresentationModel(): Message =
    Message(id, userId, content, attachment)