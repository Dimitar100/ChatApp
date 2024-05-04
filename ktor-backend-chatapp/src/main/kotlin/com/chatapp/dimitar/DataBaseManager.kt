package com.chatapp.dimitar

import org.ktorm.database.Database
import org.ktorm.dsl.*
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
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
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

}