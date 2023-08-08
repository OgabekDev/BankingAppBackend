package com.example.plugins

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    install(Resources)
    routing {
        get("/") {
            call.respond("OK")
        }

    }

    routing {
        post("api/register") {
            try {

            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }
    }
}
