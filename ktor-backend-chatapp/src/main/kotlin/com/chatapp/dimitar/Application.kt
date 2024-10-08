package com.chatapp.dimitar

import com.chatapp.dimitar.chats.ChatDataSourceSQl
import com.chatapp.dimitar.messages.ChatRoomController
import com.chatapp.dimitar.messages.MessageDataSourceSQL
import com.chatapp.dimitar.plugins.*
import com.chatapp.dimitar.security.hashing.SHA256HashingService
import com.chatapp.dimitar.security.token.JwtTokenService
import com.chatapp.dimitar.security.token.TokenConfig
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()
    val userDataSource = UserDataSourceSQL(DataBaseManager())
    val chatDataSource = ChatDataSourceSQl(DataBaseManager())
    val messageDataSource = MessageDataSourceSQL(DataBaseManager())
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(userDataSource, chatDataSource, hashingService, tokenService, tokenConfig, ChatRoomController(messageDataSource, chatDataSource, userDataSource))
}
