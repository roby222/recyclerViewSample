package com.example.roberto.recyclerviewsample

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import com.example.roberto.recyclerviewsample.persistence.Message
import com.example.roberto.recyclerviewsample.persistence.MessageDao
import com.example.roberto.recyclerviewsample.persistence.User
import com.example.roberto.recyclerviewsample.persistence.UserDao
import com.example.roberto.recyclerviewsample.utils.FakeNetworkCall
import com.example.roberto.recyclerviewsample.utils.FakeNetworkError
import com.example.roberto.recyclerviewsample.utils.FakeNetworkException
import com.example.roberto.recyclerviewsample.utils.FakeNetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class ChatRepository(val network: MainNetwork, val messagesDao: MessageDao, val userDao: UserDao) {


    val paginatedMessages: DataSource.Factory<Int, Message> by lazy<DataSource.Factory<Int, Message>>(LazyThreadSafetyMode.NONE) {
        messagesDao.loadMessagesPaginated()
    }

    val users: LiveData<List<User>> by lazy<LiveData<List<User>>>(LazyThreadSafetyMode.NONE) {
        userDao.loadUsers()
    }


    suspend fun refreshChatBox() {
        withContext(Dispatchers.IO) {
            try {
                if (messagesDao.getMessageNumber() == 0) { //TODO check query
                    val chatData = network.fetchChatData().await()
                    messagesDao.insertMessages(chatData.messages)
                    userDao.insertUsers(chatData.users)
                }
            } catch (error: FakeNetworkException) {
                throw ChatBoxRefreshError(error)
            }
        }
    }

}

class ChatBoxRefreshError(cause: Throwable) : Throwable(cause.message, cause)

suspend fun <T> FakeNetworkCall<T>.await(): T {
    return kotlin.coroutines.suspendCoroutine { continuation ->
        addOnResultListener { result ->
            when (result) {
                is FakeNetworkSuccess<T> -> continuation.resume(result.data)
                is FakeNetworkError -> continuation.resumeWithException(result.error)
            }
        }
    }
}

