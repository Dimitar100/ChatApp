package com.dimitar.chatapp.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimitar.chatapp.data.Message
import com.dimitar.chatapp.data.MessagesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    fun sendMsg(chatId: Int, message:String){

        //ChatSocketServiceImpl(AppModule.provideHttpClient()).sendMessage(message, chatId)
    }

    fun updateState(messages: List<Message>) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                //val newsItems = repository.newsItemsForCategory(category)
                _uiState.update {
                    it.copy(messages = messages)
                }
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.

            }
        }
    }

    fun getMessages(){
        val chatRepo = MessagesRepository(this)
        chatRepo.getMessages()
    }
}