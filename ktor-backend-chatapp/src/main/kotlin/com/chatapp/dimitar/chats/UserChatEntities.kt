package com.chatapp.dimitar.chats

import com.chatapp.dimitar.DBUserEntity
import com.chatapp.dimitar.DBUsersTable
import com.chatapp.dimitar.chats.DBChatsTable.bindTo
import com.chatapp.dimitar.chats.DBChatsTable.primaryKey
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBUserChatTable: Table<DBUserChatEntity>("Users"){
    val id = int("ID").primaryKey().bindTo { it.id }
    val userId = int("UserID").references(DBUsersTable){it.user}
    val chatId = int("ChatID").references(DBChatsTable){it.chat}

}

interface DBUserChatEntity: Entity<DBUserChatEntity> {

    companion object : Entity.Factory<DBUserChatEntity>()

    val id: Int
    val user: DBUserEntity
    val chat: DBChatEntity

}