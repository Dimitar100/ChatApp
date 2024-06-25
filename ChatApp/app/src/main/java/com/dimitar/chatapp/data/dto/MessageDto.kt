package com.dimitar.chatapp.data.dto

import com.dimitar.chatapp.data.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class MessageDto(
    val content: String,
    val timestamp: Long,
    val senderId: Int,
    val chatId: Int,
    val id: Int
) {
    fun toMessage(): Message {
        val date = Date(timestamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Message(
            content = content,
            formattedTime = formattedDate,
            senderId = senderId.toString(),
            chatId = chatId
        )
    }
}