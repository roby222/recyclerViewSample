package com.example.roberto.recyclerviewsample

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.example.roberto.recyclerviewsample.persistence.models.Message
import com.example.roberto.recyclerviewsample.utils.singleArgViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ChatRepository) : ViewModel() {

    companion object {
        /**
         * Factory for creating [MainViewModel]
         *
         * @param arg the repository to pass to [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }

    private val paginatedMessages = repository.paginatedMessages

    var messagesLiveData: LiveData<PagedList<Message>>

    init {
        //todo convertire
        val factory: DataSource.Factory<Int, Message> = paginatedMessages
//TODO FLAT OBJECT
        //TODO Cercare come manipolare
        //https://stackoverflow.com/questions/50058825/how-to-transform-items-in-a-pagedlistandroid-arch-component-paging-library
        //l'idea è di duplicare i messages con le informazioni sugli attachments
        val pagedListBuilder: LivePagedListBuilder<Int, Message> = LivePagedListBuilder<Int, Message>(factory, 20)
        messagesLiveData = pagedListBuilder.build()
    }

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onMainViewLoaded() {
        loadRecyclerView()

    }

    private fun loadRecyclerView() {
        launchDataLoad {
            repository.refreshChatBox()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return uiScope.launch { //TODO eliminare il try catch
            try {
                block()
            } catch (error: ChatBoxRefreshError) {
                Log.e("MainViewModel", "Failed to retrieve data")
            }
        }
    }

    fun deleteItem(messageToDelete: Message) {
        uiScope.launch {
            repository.deleteMessage(messageToDelete)
        }
    }

}
