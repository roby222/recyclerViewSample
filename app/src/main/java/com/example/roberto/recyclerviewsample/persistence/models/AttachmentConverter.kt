package com.example.roberto.recyclerviewsample.persistence.models

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object AttachmentConverter {


    @TypeConverter
    @JvmStatic
    fun stringToAttachment(data: String?): Attachment? {
        if (data.isNullOrEmpty()) {
            return null
        }
        val listType = object : TypeToken<Attachment>() {

        }.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun attachmentToString(attachment: Attachment?): String {
        if (attachment == null) {
            return ""
        }
        return Gson().toJson(attachment)
    }


}

