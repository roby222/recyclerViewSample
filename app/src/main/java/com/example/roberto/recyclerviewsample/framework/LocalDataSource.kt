package com.example.roberto.recyclerviewsample.framework

import com.example.roberto.data.MessagePersistenceSource
import com.example.roberto.data.UserPersistenceSource
import com.example.roberto.domain.Message
import com.example.roberto.domain.User

class LocalDataSource : MessagePersistenceSource, UserPersistenceSource {


    override fun getPersistedMessages(): List<Message> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveMessages(messages: List<Message>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPersistedUsers(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUsers(messages: List<User>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}