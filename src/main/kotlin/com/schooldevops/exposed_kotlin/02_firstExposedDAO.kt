package com.schooldevops.exposed_kotlin

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction


class `02_firstExposedDAO` {
}

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver="org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Cities2)

        val stPete = City.new {
            name = "St.Petersburg"
        }

        println("Cities: ${City.all()}")
    }
}

object Cities2: IntIdTable() {
    val name = varchar("name", 50)
}

class City(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<City>(Cities2)

    var name by Cities2.name
}
