package com.chatapp.dimitar

import com.chatapp.dimitar.chats.Chat
import com.chatapp.dimitar.chats.DBChatEntity
import com.chatapp.dimitar.chats.DBChatsTable
import com.chatapp.dimitar.chats.DBUserChatTable
import com.chatapp.dimitar.messages.DBMessageEntity
import com.chatapp.dimitar.messages.DBMessagesTable
import com.chatapp.dimitar.messages.Message
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.expression.SqlExpression
import java.sql.DriverManager

class DataBaseManager {

    //Connection to MySQL DB

    private val hostname = "localhost"
    private val databaseName = "ChatAppDB"
    private val username = "root"
    private val password = System.getenv("SQL_PASS")

    //private val password = System.getenv("KTOR_DB_PW")

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&allowPublicKeyRetrieval=true&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
       // ktormDatabase.
    }

    fun getAllUsers(): List<DBUserEntity>{
        return ktormDatabase.sequenceOf(DBUsersTable).toList()
    }

    fun getUser(id: Int): DBUserEntity{
        val user: DBUserEntity? = ktormDatabase.sequenceOf(DBUsersTable).find {  it.id eq id }
        return user!!
    }

    fun getUser(username: String): DBUserEntity{
        val user: DBUserEntity? = ktormDatabase.sequenceOf(DBUsersTable).find {  it.username eq username }
        return user!!
    }
    

    fun insertNewUser(user: User): Boolean {

        var res = ktormDatabase.insert(DBUsersTable){
            //set(it.id, user.id)
            set(it.username, user.username)
            set(it.salt, user.salt)
            set(it.password, user.password)
            set(it.user_role, user.userRole)
        }
        return res == 1
    }
//Chat operations ========================================================================
    fun createNewChat(creatorId: Int, userId: Int, chat: Chat): Boolean {

        val chatID = ktormDatabase.insertAndGenerateKey(DBChatsTable){
            //set(it.id, user.id)
            set(it.chatName, chat.name)
            set(it.creatorId, userId)
        }

        val res = ktormDatabase.insert(DBUserChatTable){
            set(it.userId, creatorId)
            set(it.chatId, chatID)
        }

        val res2 = ktormDatabase.insert(DBUserChatTable){
            set(it.userId, userId)
            set(it.chatId, chatID)
        }
        return res == 1 && res2 ==1
    }

    fun addParticipantToChat(user: String, chatId: Int): Boolean{
        val res = ktormDatabase.insert(DBUserChatTable){
            set(it.userId, getUser(user).id)
            set(it.chatId, chatId)
        }
        return res == 1
    }

    fun getUsersChats(userId: Int): List<DBChatEntity>{
        val temp = ktormDatabase.sequenceOf(DBUserChatTable).filter { it.userId eq userId }.toList()
        var res : List<DBChatEntity>  = ArrayList()

        for(element in temp){
            res = res.plus(element.chatId)
        }
        //temp.forEach { res.plus(it.chatId) }
        return res
    }

    //Message Operations =========================================================================================
    fun getChatsMessages(chatId: Int): List<DBMessageEntity>{
        val temp = ktormDatabase.sequenceOf(DBMessagesTable).filter { it.chatId eq chatId }.toList()
        var res : List<DBMessageEntity>  = ArrayList()

        for(element in temp){
            res = res.plus(element)
        }
        //temp.forEach { res.plus(it.chatId) }
        return res
    }

    fun insertMessage(message: Message): Boolean{
        var res = ktormDatabase.insert(DBMessagesTable){
            //set(it.id, user.id)
            set(it.content, message.content)
            set(it.chatId, message.chatId)
            set(it.senderId, message.senderId)
            set(it.timestamp, message.timestamp)
        }
        return res == 1
    }


}