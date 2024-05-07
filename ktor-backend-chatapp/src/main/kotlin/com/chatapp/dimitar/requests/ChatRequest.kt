package com.chatapp.dimitar.requests

import com.chatapp.dimitar.User
import com.chatapp.dimitar.UserDataSource
import com.chatapp.dimitar.security.hashing.HashingService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*





fun Route.createChat() {
    authenticate {
        post("chat/create") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "chat: Your userId is $userId")
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

