package com.chatapp.dimitar.chats

import com.chatapp.dimitar.DataBaseManager
import com.chatapp.dimitar.User

class ChatDataSourceSQl(private val chatDbManager: DataBaseManager): ChatDataSource {
    override suspend fun getChatByName(name: String): DBChatEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun createChat(creatorId: Int, userId: Int, chat: Chat): Boolean {
        return chatDbManager.createNewChat(creatorId, userId, chat)
    }

    override suspend fun deleteChat(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun insertParticipantInChat(user: String, chat: Int): Boolean {
        return chatDbManager.addParticipantToChat(user, chat)
    }

    override suspend fun removeParticipantInChat(user: User, chat: Chat): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserChats(userId: Int): List<DBChatEntity> {
        return chatDbManager.getUsersChats(userId)
    }
}