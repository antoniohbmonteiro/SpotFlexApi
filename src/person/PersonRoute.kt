package br.com.antoniomonteiro.person

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.person() {
    route("person") {
        get("/") {
            Database.connect(
                "jdbc:mysql://localhost:3306/spotflex_api", driver = "com.mysql.jdbc.Driver",
                user = "root", password = "Ahbm101292*"
            )
            var persons = listOf<Person>()
            transaction {
                SchemaUtils.create(PersonTable)

                persons = PersonTable.selectAll().map {
                    PersonTable.toModel(it)
                }
            }
            call.respond(persons)
        }
    }
}