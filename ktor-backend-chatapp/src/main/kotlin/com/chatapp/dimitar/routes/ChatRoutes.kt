package com.chatapp.dimitar.routes

import com.chatapp.dimitar.chats.Chat
import com.chatapp.dimitar.chats.ChatDataSource
import com.chatapp.dimitar.requests.AuthRequest
import com.chatapp.dimitar.requests.CreateChatRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*





fun Route.createChat(chatDataSource: ChatDataSource) {
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
            chatDataSource.createChat(userId!!.toInt(), chat)
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
            val temp = chats[0]
            call.respond(HttpStatusCode.OK, "chats: Your chats: $chats")
        }
    }
}

