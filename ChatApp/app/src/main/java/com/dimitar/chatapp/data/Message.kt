package com.dimitar.chatapp.data

data class Message(
    val content: String,
    val formattedTime: String,
    val senderId: Int,
    val chatId: Int
)