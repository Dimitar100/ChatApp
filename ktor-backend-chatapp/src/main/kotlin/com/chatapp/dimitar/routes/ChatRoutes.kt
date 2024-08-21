package com.chatapp.dimitar.routes

import com.chatapp.dimitar.UserDataSource
import com.chatapp.dimitar.chats.Chat
import com.chatapp.dimitar.chats.ChatDataSource
import com.chatapp.dimitar.messages.ChatRoomController
import com.chatapp.dimitar.requests.AuthRequest
import com.chatapp.dimitar.requests.CreateChatRequest
import com.chatapp.dimitar.responses.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive


fun Route.createChat(chatDataSource: ChatDataSource, userDataSource: UserDataSource, chatRoomController: ChatRoomController) {
    authenticate {
        post("chat/create") {
            val request = call.receiveNullable<CreateChatRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            val chat = Chat(
                id = 0,
                name = request.name,
                creatorId = userId!!.toInt()
            )
            chatDataSource.createChat(userId!!.toInt(), userDataSource.getUserByUsername(request.participant)!!.id, chat)
            chatRoomController.updateChatRoomsForUser(userId!!.toInt())
            call.respond(HttpStatusCode.OK, "Chat is created")
        }
    }
}

fun Route.deleteChat() {
    authenticate {
        post("chat/delete") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "chat: Your userId is $userId")
        }
    }
}

fun Route.getAllChats(chatDataSource: ChatDataSource) {
    authenticate {
        get("chat/get/all") {

            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)

            val chats = chatDataSource.getUserChats(userId!!.toInt())

        //    var responseData : List<Chat> = ArrayList()
            var responseDataJson : List<JsonObject> = ArrayList()

            chats.forEach {
               // responseData = responseData.plus(Chat(it.id, it.chatName, it.creatorId.id));
                val hashMap = hashMapOf(
                    "id" to JsonPrimitive(it.id),
                    "name" to JsonPrimitive(it.chatName),
                    "creatorId" to JsonPrimitive(it.creatorId.username)
                )
                val jsonObject = JsonObject(hashMap)
                responseDataJson = responseDataJson.plus(jsonObject)
            }

            val jsonArr = JsonArray(responseDataJson)
            call.respond(HttpStatusCode.OK, jsonArr)
        }
    }
}

