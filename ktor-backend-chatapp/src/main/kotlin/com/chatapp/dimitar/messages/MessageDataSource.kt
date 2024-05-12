package com.chatapp.dimitar.messages

import com.chatapp.dimitar.User

interface MessageDataSource {

    suspend fun sendMessage(message: Message): Boolean

    suspend fun getMessagesInChat(chatId: Int): List<DBMessageEntity>
}