package com.chatapp.dimitar.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetMessagesRequest(
    val chatId: Int,
)
