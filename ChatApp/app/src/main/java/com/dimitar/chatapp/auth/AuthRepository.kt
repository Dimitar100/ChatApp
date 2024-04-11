package com.dimitar.chatapp.auth

interface AuthRepository {
    fun signUp(username: String, password: String): AuthResult<Unit>
    fun signIn(username: String, password: String): AuthResult<Unit>
    fun authenticate(): AuthResult<Unit>
}