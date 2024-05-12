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
        sessionId: String,
        socket: WebSocketSession
    ) {
        members[user] = ChatRoomMember(
            username = user,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(senderId: Int, messageContent: String, chatId: Int) {
        members.values.forEach { member ->
            val message = Message(
                id = 0,
                content = messageContent,
                senderId = senderId,
                chatId = chatId,
                timestamp = Timestamp(System.currentTimeMillis())
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