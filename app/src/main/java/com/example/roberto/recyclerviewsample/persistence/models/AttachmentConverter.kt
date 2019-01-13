package com.example.roberto.recyclerviewsample.persistence.models

import android.arch.persistence.room.TypeConverter
import com.example.roberto.recyclerviewsample.persistence.models.dto.AttachmentDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AttachmentConverter {

    @TypeConverter
    @JvmStatic
    fun stringToAttachment(data: String?): AttachmentDTO? {
        if (data.isNullOrEmpty()) {
            return null
        }
        val listType = object : TypeToken<AttachmentDTO>() {

        }.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun attachmentToString(attachment: AttachmentDTO?): String {
        if (attachment == null) {
            return ""
        }
        return Gson().toJson(attachment)
    }
}

