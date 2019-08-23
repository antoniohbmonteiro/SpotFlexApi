package br.com.antoniomonteiro.services.product

import br.com.antoniomonteiro.base.BaseResponse
import br.com.antoniomonteiro.person.response.PersonGetResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import kotlin.math.absoluteValue


fun Route.product() {
    val db by lazy { ProductDatabase() }

    route("product") {

        get("/"){
            val a = db.getAll()
            call.respond(a)
        }

        get("/{index}") {
            val index = call.parameters["index"]?.toLong()

            val product = db.get(index = index!!.absoluteValue)

            if (product != null)
                call.respond(product)
            else
                call.respond(
                    HttpStatusCode.NotFound,
                    BaseResponse("Produto não encontrada!", "404")
                )
        }
    }
}