package com.chatapp.dimitar.messages

import com.chatapp.dimitar.User
import io.ktor.websocket.*

data class ChatRoomMember(
    val username: String,
    val sessionId: String,
    val socket: WebSocketSession
)
