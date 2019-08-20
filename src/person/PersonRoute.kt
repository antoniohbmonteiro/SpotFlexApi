package br.com.antoniomonteiro.person

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import kotlin.math.absoluteValue


val personDb by lazy { PersonDatabase() }

fun Route.person() {
    route("person") {
        get("/") {
            call.respond(personDb.getAll())
        }

        get("/{index}") {
            val index = call.parameters["index"]?.toLong()

            val person = personDb.get(index = index!!.absoluteValue)

            if (person != null)
                call.respond(person)
            else
                call.respond(status = HttpStatusCode.NoContent, message = "Não encontrado!")
        }
    }
}
