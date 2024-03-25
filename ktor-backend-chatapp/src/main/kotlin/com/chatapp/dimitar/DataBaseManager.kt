package com.chatapp.dimitar

import org.ktorm.database.Database
import java.sql.DriverManager

class DataBaseManager {

    //Connection to MySQL DB
    var con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/temp", "root", "dar11na"
    )

    private val hostname = "localhost"
    private val databaseName = "temp"
    private val username = "root"
    private val password = "dar11na"

    //private val password = System.getenv("KTOR_DB_PW")

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
    }
}