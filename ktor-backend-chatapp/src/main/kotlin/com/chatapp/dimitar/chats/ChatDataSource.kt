package com.chatapp.dimitar.chats

import com.chatapp.dimitar.DBUserEntity
import com.chatapp.dimitar.User

interface ChatDataSource {
    suspend fun getChatByName(name: String): DBChatEntity?
    suspend fun createChat(chat: Chat): Boolean
    suspend fun deleteChat(id: Int): Boolean
    suspend fun insertParticipantInChat(user: User, chat: Chat): Boolean
    suspend fun removeParticipantInChat(user: User, chat: Chat): Boolean
}