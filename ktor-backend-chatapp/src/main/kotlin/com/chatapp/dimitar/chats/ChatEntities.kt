package com.chatapp.dimitar.chats

import com.chatapp.dimitar.DBUserEntity
import com.chatapp.dimitar.DBUsersTable
import com.chatapp.dimitar.DBUsersTable.bindTo
import com.chatapp.dimitar.DBUsersTable.primaryKey
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBChatsTable: Table<DBChatEntity>("Chats"){
    val id = int("ID").primaryKey().bindTo { it.id }
    val chatName = varchar("ChatName").bindTo { it.chatName }
    val creatorId = int("CreatorID").references(DBUsersTable) { it.creatorId }
}

interface DBChatEntity: Entity<DBChatEntity> {

    companion object : Entity.Factory<DBChatEntity>()

    val id: Int
    val chatName: String
    val creatorId: DBUserEntity
}