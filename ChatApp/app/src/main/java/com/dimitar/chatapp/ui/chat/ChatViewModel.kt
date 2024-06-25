package com.dimitar.chatapp.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.dimitar.chatapp.chat.ChatSocketServiceImpl
import com.dimitar.chatapp.data.ChatRepository
import com.dimitar.chatapp.di.AppModule
import com.dimitar.chatapp.signup.RegisterRepository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun sendMsg(chatId: Int, message:String){

        //ChatSocketServiceImpl(AppModule.provideHttpClient()).sendMessage(message, chatId)
    }

    fun getMessages(){
        val chatRepo = MessagesRepository(this)
        chatRepo.getMessages()
    }
}