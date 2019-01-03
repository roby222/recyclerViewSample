package com.example.roberto.recyclerviewsample.ui

import com.example.roberto.recyclerviewsample.ui.models.Message
import com.example.roberto.recyclerviewsample.ui.models.User
import com.example.roberto.recyclerviewsample.ui.models.toPresentationModel
import com.example.roberto.usecases.GetMessages
import com.example.roberto.usecases.GetUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.roberto.domain.Message as DomainMessage
import com.example.roberto.domain.User as DomainUser


class MainPresenter(
    private var view: View?,
    private val getMessages: GetMessages,
    private val getUsers: GetUsers
) {
    interface View {
        fun renderMessages(messages: List<Message>, users: List<User>)
        fun showLoading()
        fun hideLoading()
    }

    private val mainPresenterJob = Job() //TODO check coroutines
    private val uiScope = CoroutineScope(Dispatchers.Main + mainPresenterJob)

    fun onCreate() = uiScope.launch {
        //TODO check coroutines
        view?.showLoading()

        val messages = getMessages()
        val users = getUsers()
        view?.renderMessages(
            messages.map(DomainMessage::toPresentationModel),
            users.map(DomainUser::toPresentationModel)
        )
        view?.hideLoading()
    }

    fun onDestroy() {
        view = null
        mainPresenterJob.cancel() //TODO check clean coroutine
    }
}