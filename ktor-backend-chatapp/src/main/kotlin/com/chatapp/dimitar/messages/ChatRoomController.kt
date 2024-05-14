package com.chatapp.dimitar.messages

import com.chatapp.dimitar.UserDataSource
import com.chatapp.dimitar.chats.ChatDataSource
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.Timestamp
import java.util.concurrent.ConcurrentHashMap

class ChatRoomController(
    private val messageDataSource: MessageDataSource,
    private val chatDataSource: ChatDataSource,
    private val userDataSource: UserDataSource
) {

    private val chatRooms = ConcurrentHashMap<Int, ConcurrentHashMap<String, ChatRoomMember>>()



    suspend fun onJoin(
        user: String,
        sessionId: String,
        socket: WebSocketSession
    ) {

        val member = ChatRoomMember(
            username = user,
            sessionId = sessionId,
            socket = socket
        )
        val usersChats = chatDataSource.getUserChats(userDataSource.getUserByUsername(user)!!.id)
        usersChats.forEach {
            if(chatRooms.containsKey(it.id)){
                chatRooms[it.id]!![user] = member
            }else{
                val members = ConcurrentHashMap<String, ChatRoomMember>()
                members[user] = member
                chatRooms[it.id] = members
            }
        }
    }

    suspend fun sendMessage(senderUsername: String, content: String, chatId: Int) {
        chatRooms[chatId]!!.values.forEach{ chatRoomMember ->
            val message = Message(
                id = 0,
                content = content,
                senderId = userDataSource.getUserByUsername(senderUsername)!!.id,
                chatId = chatId,
                timestamp = System.currentTimeMillis()
            )
            messageDataSource.sendMessage(message)

            val parsedMessage = Json.encodeToString(message)
            chatRoomMember.socket.send(Frame.Text(parsedMessage))

        }
    }

    suspend fun getAllMessages(chatId : Int): List<DBMessageEntity> {
        return messageDataSource.getMessagesInChat(chatId)
    }

    suspend fun tryDisconnect(username: String) {
        chatRooms.forEach{
            it.value[username]?.socket?.close()
            if(it.value.containsKey(username)) {
                it.value.remove(username)
            }
        }
    }
}