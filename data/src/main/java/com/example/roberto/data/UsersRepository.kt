package com.example.roberto.data

import com.example.roberto.domain.User

class UsersRepository(

    private val userPersistenceSource: UserPersistenceSource,
    private val userRemoteSource: UserRemoteSource
) {
    fun getSavedUsers(): List<User> = userPersistenceSource.getPersistedUsers()

    fun requestUsers(): List<User> {
        val users = userRemoteSource.getRemoteUsers()
        userPersistenceSource.saveUsers(users)
        return getSavedUsers()
    }
}

interface UserPersistenceSource {
    fun getPersistedUsers(): List<User>
    fun saveUsers(messages: List<User>)
}

interface UserRemoteSource {
    fun getRemoteUsers(): List<User>
}

