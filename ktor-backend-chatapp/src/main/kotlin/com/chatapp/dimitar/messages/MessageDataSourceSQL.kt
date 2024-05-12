package com.chatapp.dimitar.messages

import com.chatapp.dimitar.DataBaseManager

class MessageDataSourceSQL(private val messageDbManager: DataBaseManager) :MessageDataSource {

    override suspend fun sendMessage(message: Message): Boolean {
        return messageDbManager.sendMessage(message)
    }

    override suspend fun getMessagesInChat(chatId: Int): List<DBMessageEntity> {
        return messageDbManager.getChatsMessages(chatId)
    }
}