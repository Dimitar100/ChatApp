package com.chatapp.dimitar.responses

import com.chatapp.dimitar.security.token.TokenConfig
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
