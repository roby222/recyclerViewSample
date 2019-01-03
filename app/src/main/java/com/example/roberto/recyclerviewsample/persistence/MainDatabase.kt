package com.example.roberto.recyclerviewsample.persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.content.Context
import com.google.gson.annotations.SerializedName
import java.util.*

//TODO che le spostiamo da qua?
@Entity
data class Message(
    @PrimaryKey val id: Long, val userId: Long,
    val content: String,
    @TypeConverters(Converters::class)
    @SerializedName("attachment")
    private val _attachment: List<Attachment>?
) {
    val attachment
        get() = if (_attachment.isNullOrEmpty()) Collections.emptyList() else _attachment
}

@Entity
data class Attachment(@PrimaryKey val id: String, val title: String, val url: String, val thumbnailUrl: String)

@Entity
data class User(@PrimaryKey val id: Long, val name: String, val avatarId: String)

data class ChatData(val messages: List<Message>, val users: List<User>)

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(messages: List<User>)

    @Query("select * from user")
    fun loadUsers(): LiveData<List<User>>
}

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessages(messages: List<Message>)

    @Query("select * from Message")
    fun loadMessages(): LiveData<List<Message>>

    @Query("select COUNT(*) from Message")
    fun getMessageNumber(): Int
}

/**
 * TitleDatabase provides a reference to the dao to repositories
 */
@Database(entities = [Message::class, User::class, Attachment::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ChatDatabase : RoomDatabase() {
    abstract val messageDao: MessageDao
    abstract val userDao: UserDao
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
