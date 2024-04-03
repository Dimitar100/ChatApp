package com.chatapp.dimitar

interface UserDataSource {
    suspend fun getUserByUsername(username: String): DBUserEntity?
    suspend fun insertUser(user: User): Boolean
    suspend fun getNewUserId(): Int
}