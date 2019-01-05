package com.example.roberto.recyclerviewsample.persistence

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import android.content.Context
import com.google.gson.annotations.SerializedName
import java.util.*

//TODO che le spostiamo da qua?
@Entity
data class Message(
    @PrimaryKey val id: Long,
    val userId: Long,
    val content: String,
    @TypeConverters(Converters::class)
    @SerializedName("attachment")
    private val _attachment: List<Attachment>?,
    var userName: String?, //TODO
    var userAvatar: String? //TODO
) {
    val attachment
        get() = if (_attachment.isNullOrEmpty()) Collections.emptyList() else _attachment
}

@Entity
data class Attachment(@PrimaryKey val id: String, val title: String, val url: String, val thumbnailUrl: String)

data class User(val id: Long, val name: String, val avatarId: String)

data class ChatData(val messages: List<Message>, val users: List<User>)

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
@TypeConverters(Converters::class)
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


