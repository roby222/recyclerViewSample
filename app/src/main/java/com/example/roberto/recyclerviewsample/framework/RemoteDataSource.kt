package com.example.roberto.recyclerviewsample.framework

import com.example.roberto.data.MessageRemoteSource
import com.example.roberto.data.UserRemoteSource
import com.example.roberto.domain.Message
import com.example.roberto.domain.User

class RemoteDataSource : MessageRemoteSource, UserRemoteSource {

    override fun getRemoteMessages(): List<Message> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRemoteUsers(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}