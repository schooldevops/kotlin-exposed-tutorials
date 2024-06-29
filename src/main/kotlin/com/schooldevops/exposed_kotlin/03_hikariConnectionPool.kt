package com.schooldevops.exposed_kotlin

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class `03_hikariConnectionPool` {
}

fun main(args: Array<String>) {

    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://localhost:3306/test2db"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = "root"
        password = "root1234"
        maximumPoolSize = 6
        isReadOnly = false
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }
    val datasource = HikariDataSource(config)
    Database.connect(datasource = datasource, databaseConfig = DatabaseConfig {

    })

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Cities3)

        val stPete = City2.new {
            name = "St.Petersburg"
        }

        println("Cities: ${City2.all()}")
    }
}

object Cities3: IntIdTable() {
    val name = varchar("name", 50)
}

class City2(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<City2>(Cities3)

    var name by Cities3.name
}