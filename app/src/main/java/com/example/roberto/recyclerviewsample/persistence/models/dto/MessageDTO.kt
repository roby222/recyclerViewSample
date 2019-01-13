package com.example.roberto.recyclerviewsample.persistence.models.dto

data class MessageDTO(val id: Long, val userId: Long, val content: String, val attachments: List<AttachmentDTO>?)
