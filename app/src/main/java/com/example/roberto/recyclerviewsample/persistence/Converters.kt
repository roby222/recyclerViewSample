package com.example.roberto.recyclerviewsample.persistence

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


object Converters {

    @TypeConverter
    fun stringToAttachmentList(data: String): List<Attachment> {
        if (data.isNullOrEmpty()) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Attachment>>() {

        }.getType()
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun AttachmentListToString(listAttachment: List<Attachment>): String {
        return Gson().toJson(listAttachment)
    }
}