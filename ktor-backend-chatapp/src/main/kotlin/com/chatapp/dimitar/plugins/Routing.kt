package com.chatapp.dimitar.plugins

import com.chatapp.dimitar.DataBaseManager
import com.chatapp.dimitar.*
import com.chatapp.dimitar.requests.createChat
import com.chatapp.dimitar.requests.deleteChat
import com.chatapp.dimitar.security.hashing.HashingService
import com.chatapp.dimitar.security.token.TokenConfig
import com.chatapp.dimitar.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        get("/") {
            //call.respondText("Hello World!")
            val dbManager = DataBaseManager()
            call.respondText(dbManager.getAllUsers().toString())
        }
        this@routing.signIn(userDataSource, hashingService, tokenService, tokenConfig)
        this@routing.signUp(hashingService, userDataSource)
        this@routing.authenticate()
        this@routing.getSecretInfo()
        this@routing.createChat()
        this@routing.deleteChat()
    }
}
