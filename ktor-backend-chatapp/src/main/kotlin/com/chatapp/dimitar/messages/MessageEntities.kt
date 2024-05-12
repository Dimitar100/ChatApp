package com.chatapp.dimitar.messages

import com.chatapp.dimitar.DBUserEntity
import com.chatapp.dimitar.DBUsersTable
import com.chatapp.dimitar.chats.DBChatEntity
import com.chatapp.dimitar.chats.DBChatsTable
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.sql.Time
import java.sql.Timestamp

object DBMessagesTable: Table<DBMessageEntity>("Messages"){
    val id = int("ID").primaryKey().bindTo { it.id }
    val content = varchar("Content").bindTo { it.content }
    val senderId = int("SenderID").references(DBUsersTable) { it.senderId }
    val chatId = int("ChatID").references(DBChatsTable) { it.chatId }
    //val timestamp = timestamp("Time_of_sending").bindTo { it.timestamp }
    val timestamp = jdbcTimestamp("Time_of_sending").bindTo { it.timestamp }
}

interface DBMessageEntity: Entity<DBMessageEntity> {

    companion object : Entity.Factory<DBChatEntity>()

    val id: Int
    val content: String
    val senderId: DBUserEntity
    val chatId: DBChatEntity
    val timestamp: Timestamp
}