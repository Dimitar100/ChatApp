package com.chatapp.dimitar


data class User(
    val id: Int,
    val username: String,
    val password: String,
    val salt: String
)