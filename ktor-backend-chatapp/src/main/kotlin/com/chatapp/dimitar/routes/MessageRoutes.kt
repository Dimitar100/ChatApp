package com.chatapp.dimitar.routes

import com.chatapp.dimitar.messages.ChatRoomController
import com.chatapp.dimitar.messages.Connection
import com.chatapp.dimitar.sessions.ChatSession
import com.chatapp.dimitar.signIn
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.util.*
import kotlin.collections.LinkedHashSet

fun Route.chatSocket(roomController: ChatRoomController) {
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if(session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }

        try {
            roomController.onJoin(
                user = session.username,
                socket = this,
                sessionId = session.sessionId
            )
            incoming.consumeEach { frame ->
                if(frame is Frame.Text) {
                    roomController.sendMessage(
                        senderUsername = session.username,//session.username,
                        content=  frame.readText(),
                        chatId = call.parameters["chatId"]!!.toInt()
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            roomController.tryDisconnect(call.parameters["username"].toString())
        }
    }
}

fun Route.getAllMessages(roomController: ChatRoomController) {
    authenticate {
        get("/chat/messages") {
            call.respond(
                HttpStatusCode.OK,
                roomController.getAllMessages(0)
            )

        }
    }
}