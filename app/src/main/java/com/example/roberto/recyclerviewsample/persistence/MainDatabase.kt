package com.example.roberto.recyclerviewsample.persistence

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import android.content.Context
import com.google.gson.annotations.SerializedName
import java.util.*

//TODO che le spostiamo da qua?
//TODO classe dto
@Entity
data class Message(
    @PrimaryKey val id: Long,
    val messageId: Long,
    val userId: Long,
    val content: String,
    @TypeConverters(ListAttachmentConverter::class)
    @SerializedName("attachments")
    private val _attachment: List<Attachment>?,
    var userName: String?, //TODO
    var userAvatar: String?, //TODO
    @TypeConverters(AttachmentConverter::class)
    private val _attachmentSpaghetti: Attachment?,
    val isAnAttachment: Boolean
) {
    val attachment
        get() = if (_attachment.isNullOrEmpty()) Collections.emptyList() else _attachment
    val attachmentSpaghetti
        get() = _attachmentSpaghetti
}

@Entity
data class Attachment(@PrimaryKey val id: String, val title: String, val url: String, val thumbnailUrl: String)

data class User(val id: Long, val name: String, val avatarId: String)

data class ChatData(val messages: List<MessageDTO>, val users: List<User>)


data class MessageDTO(val id: Long, val userId: Long, val content: String, val attachments: List<Attachment>?)

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessagesCustom(messages: List<Message>)

    @Query("select * from Message")
    fun loadMessagesPaginated(): DataSource.Factory<Int, Message>

    @Query("select COUNT(*) from Message")
    fun getMessageNumber(): Int

    @Delete
    fun delete(person: Message)

    //TODO UPDATE/DELETE enrichment
}

/**
 * TitleDatabase provides a reference to the dao to repositories
 */
@Database(entities = [Message::class, Attachment::class], version = 1, exportSchema = false)
@TypeConverters(ListAttachmentConverter::class, AttachmentConverter::class)
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


