package com.schooldevops.exposed_kotlin

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class `01_firstExposed` {
}

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Cities)

        val stPeteId = Cities.insert {
            it[name] = "St. Petersburg"
        } get  Cities.id

        println("Cities: ${Cities.selectAll()} and id: $stPeteId")
        Cities.selectAll().forEach(::println)
    }
}

object Cities: IntIdTable() {
    val name = varchar("name", 50)
}