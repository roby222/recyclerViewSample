package com.example.roberto.recyclerviewsample.persistence

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.roberto.recyclerviewsample.MainNetworkImpl
import com.example.roberto.recyclerviewsample.persistence.dao.ChatElementDao
import com.example.roberto.recyclerviewsample.persistence.models.AttachmentConverter
import com.example.roberto.recyclerviewsample.persistence.models.ChatElement
import com.example.roberto.recyclerviewsample.persistence.models.dto.AttachmentDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ChatElement::class, AttachmentDTO::class], version = 1, exportSchema = false)
@TypeConverters(AttachmentConverter::class)
abstract class ChatDatabase : RoomDatabase() {
    abstract val chatElementDao: ChatElementDao
}

private lateinit var INSTANCE: ChatDatabase

fun getDatabase(context: Context): ChatDatabase {
    synchronized(ChatDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_db"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        CoroutineScope(Dispatchers.IO).launch {
                            ChatRepository(MainNetworkImpl(context), INSTANCE.chatElementDao).loadChatElements()
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}




