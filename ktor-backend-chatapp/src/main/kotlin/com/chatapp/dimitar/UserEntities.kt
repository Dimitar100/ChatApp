package com.chatapp.dimitar

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBUsersTable: Table<DBUserEntity>("Users"){
    val id = int("ID").primaryKey().bindTo { it.id }
    val username = varchar("Username").bindTo { it.username }
    val password = varchar("Pass").bindTo { it.password }
    val user_role = varchar("User_role").bindTo { it.userRole }
    val salt = varchar("Salt").bindTo { it.salt}

}

interface DBUserEntity: Entity<DBUserEntity>{

    companion object : Entity.Factory<DBUserEntity>()

    val id: Int
    val username: String
    val password: String
    val salt: String
    val userRole: String

}