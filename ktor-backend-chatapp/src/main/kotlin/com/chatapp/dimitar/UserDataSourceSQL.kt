package com.chatapp.dimitar

import com.chatapp.dimitar.DBUserEntity
import com.chatapp.dimitar.DataBaseManager
import com.chatapp.dimitar.User
import com.chatapp.dimitar.UserDataSource

class UserDataSourceSQL(private val userDbManager: DataBaseManager) : UserDataSource {

    override suspend fun getUserByUsername(username: String): DBUserEntity? {

        return userDbManager.getUser(username)
    }

    override suspend fun insertUser(user: User): Boolean {
        return userDbManager.insertNewUser(user)
    }

    override suspend fun getNewUserId(): Int {
        return 3
    }
}