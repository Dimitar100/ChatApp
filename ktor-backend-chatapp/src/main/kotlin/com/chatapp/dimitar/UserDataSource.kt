package com.chatapp.dimitar

interface UserDataSource {
    suspend fun getUserByUsername(username: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun getNewUserId(): Int
}