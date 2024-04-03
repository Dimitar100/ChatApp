package com.chatapp.dimitar

import org.ktorm.database.Database
import org.ktorm.dsl.AssignmentsBuilder
import org.ktorm.dsl.insert
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import java.sql.DriverManager

class DataBaseManager {

    //Connection to MySQL DB
    var con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/temp", "root", "dar11na"
    )

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
    }

    fun getAllUsers(): List<DBUserEntity>{
        return ktormDatabase.sequenceOf(DBUsersTable).toList()
    }

    fun getUser(id: Int): DBUserEntity{
        var user: DBUserEntity? = null
        val users: List<DBUserEntity> = ktormDatabase.sequenceOf(DBUsersTable).toList()
        for(u in users){
            if(id == u.id){
                user = u
            }
        }
        return user!!
    }

    fun getUser(username: String): DBUserEntity{
        var user: DBUserEntity? = null
        val users: List<DBUserEntity> = ktormDatabase.sequenceOf(DBUsersTable).toList()
        for(u in users){
            if(username == u.username){
                user = u
            }
        }
        return user!!
    }

    fun insertNewUser(user: User): Boolean {

        var res = ktormDatabase.insert(DBUsersTable){
            set(it.id, user.id)
            set(it.username, user.username)
            set(it.salt, user.salt)
            set(it.password, user.password)
            set(it.user_role, user.userRole)
        }
        return res == 1
    }

}