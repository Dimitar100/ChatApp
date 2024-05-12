package com.chatapp.dimitar.routes

import com.chatapp.dimitar.messages.ChatRoomController
import com.chatapp.dimitar.signIn
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(roomController: ChatRoomController) {
    webSocket("/chat-socket") {
        val session = call

        try {
            roomController.onJoin(
                user = "mitko",
                socket = this
            )
            incoming.consumeEach { frame ->
                if(frame is Frame.Text) {
                    roomController.sendMessage(
                        senderId = 2,
                        content=  frame.readText(),
                        chatId = 0
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            roomController.tryDisconnect("mitko")
        }
    }
}

/*fun Route.getAllMessages(roomController: ChatRoomController) {
    authenticate {
        get("/chat/messages") {
            call.respond(
                HttpStatusCode.OK,
                roomController.getAllMessages(0)
            )

        }
    }
}*/