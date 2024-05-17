package com.dimitar.chatapp.data

import com.dimitar.chatapp.ui.home.HomeViewModel
import okhttp3.OkHttpClient

class MessageRepository(
    private val viewModel: HomeViewModel,
) {
    private val client = OkHttpClient()
    private val httpServerUrl = "http://10.0.2.2:6789/"
    private val token = "Bearer ${viewModel.getJWT()}"

    fun getAllMessages(chatId: Int){}

    fun sendMessage(sender: String, chatId: Int){

    }
}