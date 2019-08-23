package br.com.antoniomonteiro.services.category

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


fun Route.category() {
    val db by lazy { CategoryDatabase() }

    route("category") {

        get("/"){
            call.respond(db.getAll())
        }

        get("/{index}"){
            val index = call.parameters["index"]?.toLong()

            val category = db.get(index = index!!.absoluteValue)

            if (category != null)
                call.respond(category)
            else
                call.respond(
                    HttpStatusCode.NotFound,
                    BaseResponse("Categoria não encontrada!", "404")
                )
        }

        post("/"){
            val category = call.receive<Category>()

            val categoryId = db.add(category)

            call.respond(categoryId)
        }

    }
}