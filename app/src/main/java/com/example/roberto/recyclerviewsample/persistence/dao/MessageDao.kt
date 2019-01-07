package com.example.roberto.recyclerviewsample.persistence.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.example.roberto.recyclerviewsample.persistence.models.Message

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