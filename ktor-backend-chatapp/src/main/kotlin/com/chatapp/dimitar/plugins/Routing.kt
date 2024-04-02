package com.chatapp.dimitar.plugins

import com.chatapp.dimitar.DataBaseManager
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            //call.respondText("Hello World!")
            val dbManager = DataBaseManager()
            call.respondText(dbManager.getAllUsers().toString())
        }
    }
}
