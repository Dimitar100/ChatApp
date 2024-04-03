package com.chatapp.dimitar.requests

import com.chatapp.dimitar.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)
