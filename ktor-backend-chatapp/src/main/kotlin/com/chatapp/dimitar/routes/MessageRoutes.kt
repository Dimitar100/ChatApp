package com.chatapp.dimitar.routes

import com.chatapp.dimitar.messages.ChatRoomController
import com.chatapp.dimitar.messages.OnlineUsers
import com.chatapp.dimitar.sessions.ChatSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(roomController: ChatRoomController) {
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if(session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }
        if(OnlineUsers.isUserOnline(session.username)) {
            try {
                roomController.onJoin(
                    user = session.username,
                    socket = this,
                    sessionId = session.sessionId
                )
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        roomController.sendMessage(
                            senderUsername = session.username,//session.username,
                            content = frame.readText(),
                            chatId = call.parameters["chatId"]!!.toInt()
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                roomController.tryDisconnect(call.parameters["username"].toString())
            }
        }else{
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "ERROR"))
            return@webSocket
        }
    }
}

fun Route.getAllMessages(roomController: ChatRoomController) {
    authenticate {
        get("/chat/messages") {
            call.respond(
                HttpStatusCode.OK,
                roomController.getAllMessages(call.parameters["chatId"]!!.toInt())
            )

        }
    }
}