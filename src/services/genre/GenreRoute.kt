package br.com.antoniomonteiro.services.genre

import br.com.antoniomonteiro.base.BaseResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import kotlin.math.absoluteValue


fun Route.genre() {
    val db by lazy { GenreDatabase() }

    route("genre") {

        get("/"){
            call.respond(db.getAll())
        }

        get("/{index}"){
            val index = call.parameters["index"]?.toLong()

            val genre = db.get(index = index!!.absoluteValue)

            if (genre != null)
                call.respond(genre)
            else
                call.respond(
                    HttpStatusCode.NotFound,
                    BaseResponse("Genero não encontrada!", "404")
                )
        }

        post("/"){
            val genre = call.receive<Genre>()

            val genreId = db.add(genre)

            call.respond(genreId)
        }

    }
}