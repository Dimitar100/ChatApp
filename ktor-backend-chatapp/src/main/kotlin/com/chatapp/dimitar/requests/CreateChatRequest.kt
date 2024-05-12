package com.chatapp.dimitar.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val name: String,
    val participant: String
)
