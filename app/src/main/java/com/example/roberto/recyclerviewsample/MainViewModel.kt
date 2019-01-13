package com.example.roberto.recyclerviewsample

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.roberto.recyclerviewsample.persistence.ChatRepository
import com.example.roberto.recyclerviewsample.persistence.models.ChatElement
import com.example.roberto.recyclerviewsample.utils.singleArgViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE_HINT = 20

class MainViewModel(private val repository: ChatRepository) : ViewModel() {


    companion object {
        /**
         * Factory for creating [MainViewModel]
         *
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
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

    private val paginatedChatElements = repository.paginatedChatElements

    var chatElementsLiveData: LiveData<PagedList<ChatElement>>

    init {
        val factory: DataSource.Factory<Int, ChatElement> = paginatedChatElements
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
            .setPageSize(PAGE_SIZE)
            .build()

        val pagedListBuilder: LivePagedListBuilder<Int, ChatElement> =
            LivePagedListBuilder<Int, ChatElement>(factory, pagedListConfig)
        chatElementsLiveData = pagedListBuilder.build()
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun deleteItem(chatElementToDelete: ChatElement) {
        uiScope.launch {
            repository.deleteChatElement(chatElementToDelete)
        }
    }

}
