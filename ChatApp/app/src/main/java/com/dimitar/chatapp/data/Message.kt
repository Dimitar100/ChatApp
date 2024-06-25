package com.dimitar.chatapp.data

data class Message(
    val content: String,
    val formattedTime: String,
    val senderId: String,
    val chatId: Int
)