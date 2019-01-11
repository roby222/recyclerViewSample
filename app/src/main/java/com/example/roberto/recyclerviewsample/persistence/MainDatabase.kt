package com.example.roberto.recyclerviewsample.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.roberto.recyclerviewsample.persistence.dao.MessageDao
import com.example.roberto.recyclerviewsample.persistence.models.Attachment
import com.example.roberto.recyclerviewsample.persistence.models.AttachmentConverter
import com.example.roberto.recyclerviewsample.persistence.models.Message


/**
 * TitleDatabase provides a reference to the dao to repositories
 */
@Database(entities = [Message::class, Attachment::class], version = 1, exportSchema = false)
@TypeConverters(AttachmentConverter::class)
abstract class ChatDatabase : RoomDatabase() {
    abstract val messageDao: MessageDao
}

private lateinit var INSTANCE: ChatDatabase

/**
 * Instantiate a database from a context.
 */
fun getDatabase(context: Context): ChatDatabase {
    synchronized(ChatDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_db"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}


