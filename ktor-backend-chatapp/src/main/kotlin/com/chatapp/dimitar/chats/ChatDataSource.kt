package com.chatapp.dimitar.chats

import com.chatapp.dimitar.DBUserEntity
import com.chatapp.dimitar.User

interface ChatDataSource {
    suspend fun getChatByName(name: String): DBChatEntity?
    suspend fun createChat(creatorId: Int, userId: Int, chat: Chat): Boolean
    suspend fun deleteChat(id: Int): Boolean
    suspend fun insertParticipantInChat(user: String, chat: Int): Boolean
    suspend fun removeParticipantInChat(user: User, chat: Chat): Boolean
    suspend fun getUserChats(userId: Int): List<DBChatEntity>
    suspend fun getParticipantsInChats(chatId: Int): List<DBUserEntity>
}