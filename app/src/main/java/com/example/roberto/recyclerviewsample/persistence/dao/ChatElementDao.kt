package com.example.roberto.recyclerviewsample.persistence.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.example.roberto.recyclerviewsample.persistence.models.ChatElement

@Dao
interface ChatElementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatElements: List<ChatElement>)

    @Query("select * from ChatElement")
    fun load(): DataSource.Factory<Int, ChatElement>

    @Delete
    fun delete(chatElement: ChatElement)
}