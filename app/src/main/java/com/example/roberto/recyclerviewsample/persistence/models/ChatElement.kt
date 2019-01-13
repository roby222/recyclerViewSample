package com.example.roberto.recyclerviewsample.persistence.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.roberto.recyclerviewsample.persistence.models.dto.AttachmentDTO

@Entity
data class ChatElement(
    @PrimaryKey val id: Long,
    val messageId: Long,
    val userId: Long,
    val content: String,
    var userName: String?,
    var userAvatar: String?,
    @TypeConverters(AttachmentConverter::class)
    private val _attachment: AttachmentDTO?,
    val isAnAttachment: Boolean
) {
    val attachment
        get() = _attachment
    val isOwnMessage
        get() = userId == 1L
}