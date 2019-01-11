package com.example.roberto.recyclerviewsample.persistence.models

data class MessageDTO(val id: Long, val userId: Long, val content: String, val attachments: List<Attachment>?)
