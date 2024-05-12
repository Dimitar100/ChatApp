package com.chatapp.dimitar.messages

import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Message(
    val id: Int,
    val content: String,
    val senderId: Int,
    val chatId: Int,
    val timestamp: Long
)
