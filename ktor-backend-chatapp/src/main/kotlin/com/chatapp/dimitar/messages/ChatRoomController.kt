package com.chatapp.dimitar.messages

import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.Timestamp
import java.util.concurrent.ConcurrentHashMap

class ChatRoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, ChatRoomMember>()

    fun onJoin(
        user: String,
        socket: WebSocketSession
    ) {
        members[user] = ChatRoomMember(
            username = user,
            socket = socket
        )
    }

    suspend fun sendMessage(senderId: Int, content: String, chatId: Int) {
        members.values.forEach { member ->
            val message = Message(
                id = 0,
                content = content,
                senderId = senderId,
                chatId = chatId,
                timestamp = System.currentTimeMillis()
            )
            messageDataSource.sendMessage(message)

            val parsedMessage = Json.encodeToString(message)
            member.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessages(chatId : Int): List<DBMessageEntity> {
        return messageDataSource.getMessagesInChat(chatId)
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if(members.containsKey(username)) {
            members.remove(username)
        }
    }
}