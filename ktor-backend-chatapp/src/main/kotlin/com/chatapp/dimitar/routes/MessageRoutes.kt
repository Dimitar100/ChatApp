package com.chatapp.dimitar.routes

import com.chatapp.dimitar.messages.ChatRoomController
import com.chatapp.dimitar.messages.IncomingMessage
import com.chatapp.dimitar.messages.OnlineUsers
import com.chatapp.dimitar.requests.AuthRequest
import com.chatapp.dimitar.requests.GetMessagesRequest
import com.chatapp.dimitar.sessions.ChatSession
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

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
                        val frameText = frame.readText()
                        val incomingMessage: IncomingMessage = Gson().fromJson(frameText, IncomingMessage::class.java)
                        roomController.sendMessage(
                            senderUsername = session.username,//session.username,
                            content = incomingMessage.message,
                            chatId = incomingMessage.chatId
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
            val request = call.receiveNullable<GetMessagesRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val chatMessages =  roomController.getAllMessages(request.chatId)

            //    var responseData : List<Chat> = ArrayList()
            var responseDataJson : List<JsonObject> = ArrayList()

            chatMessages.forEach {
                // responseData = responseData.plus(Chat(it.id, it.chatName, it.creatorId.id));
                val hashMap = hashMapOf(
                    "id" to JsonPrimitive(it.id),
                    "content" to JsonPrimitive(it.content),
                    "senderId" to JsonPrimitive(it.senderId.username),
                    "timestamp" to JsonPrimitive(it.timestamp.toString())
                )
                val jsonObject = JsonObject(hashMap)
                responseDataJson = responseDataJson.plus(jsonObject)
            }

            val jsonArr = JsonArray(responseDataJson)

            call.respond(
                HttpStatusCode.OK,
                jsonArr
            )

        }
    }
}