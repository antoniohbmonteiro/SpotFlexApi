package br.com.antoniomonteiro.services.person

import br.com.antoniomonteiro.base.BaseResponse
import br.com.antoniomonteiro.services.person.response.PersonGetAllResponse
import br.com.antoniomonteiro.person.response.PersonGetResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import kotlin.math.absoluteValue


val db by lazy { PersonDatabase() }

fun Route.person() {
    route("person") {
        get("/") {
            call.respond(PersonGetAllResponse(db.getAll()))
        }

        get("/{index}") {
            val index = call.parameters["index"]?.toLong()

            val person = db.get(index = index!!.absoluteValue)

            if (person != null)
                call.respond(PersonGetResponse(person))
            else
                call.respond(
                    HttpStatusCode.NotFound,
                    BaseResponse("Pessoa não encontrada!", "404")
                )
        }

        post("/") {
            val person = call.receive<Person>()

            val personId = db.add(person)

            call.respond(PersonGetResponse(personId))
        }

        delete("/{index}") {
            call.parameters["index"]?.toLong()?.let {
                db.delete(it)
            }

            call.respond(HttpStatusCode.OK)
        }

    }
}
