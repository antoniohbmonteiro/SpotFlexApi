package br.com.antoniomonteiro

import br.com.antoniomonteiro.person.person
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Routing) {
        person()






        var a = 1
    }

    routing {
        get("/") {
            Database.connect(
                "jdbc:mysql://localhost:3306/spotflex_api", driver = "com.mysql.jdbc.Driver",
                user = "root", password = "Ahbm101292*"
            )

            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

