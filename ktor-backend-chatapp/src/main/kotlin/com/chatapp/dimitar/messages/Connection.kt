package com.chatapp.dimitar.messages

import io.ktor.websocket.*
import java.util.concurrent.atomic.*

class Connection(
    val session: DefaultWebSocketSession,
    val username: String) {
    companion object {
        val lastId = AtomicInteger(0)
    }
    val id = lastId.getAndIncrement()
}