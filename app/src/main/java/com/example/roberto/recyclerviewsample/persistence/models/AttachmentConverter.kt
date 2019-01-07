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

        }.getType()
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun AttachmentToString(attachment: Attachment?): String {
        if (attachment == null) {
            return ""
        }
        return Gson().toJson(attachment)
    }



}

