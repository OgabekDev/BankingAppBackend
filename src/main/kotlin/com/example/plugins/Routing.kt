package com.example.plugins

import com.example.db.Database
import com.example.model.Card
import com.example.model.Login
import com.example.model.Register
import com.example.model.ReturnType
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Resources)

    val db = Database()

    routing {
        get("/") {
            call.respond("OK")
        }

    }

    routing {
        post("api/register") {
            try {
                val user = call.receive<Register>()
                call.respond(status = HttpStatusCode.Created, db.createUser(System.currentTimeMillis(), user.firstName, user.lastName, user.phoneNumber, user.password))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }
    }

    routing {
        post("api/login") {
            try {
                val user = call.receive<Login>()
                call.respond(status = HttpStatusCode.OK, db.login(user.userID, user.password))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }
    }

    routing {   // Balance APIs
        get("api/{userId}/balance") {
            try {
                val userId = call.parameters["userId"]!!.toLong()
                call.respond(db.getUserBalance(userId))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }

        get("api/cards/{cardId}/balance") {
            try {
                val cardId = call.parameters["cardId"]!!.toString()
                call.respond(db.getCardBalance(cardId))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }
    }

    routing {   // History APIs
        get("api/{userId}/history") {
            try {
                val userId = call.parameters["userId"]!!.toLong()
                val count = call.request.queryParameters["count"]!!.toInt()

                val result = db.getHistories(userId)

                call.respond(ReturnType(result.status, result.body?.take(count)))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }

        get("api/history/{id}") {
            try {
                val id = call.parameters["id"]!!.toLong()
                call.respond(db.getHistory(id))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }
    }


    routing {   // Card APIs
        post("api/{userId}/addCard") {
            try {
                val userId = call.parameters["userId"]!!.toLong()
                val card = call.receive<Card>()

                call.respond(status = HttpStatusCode.Created, db.addCard(card.name, card.number, card.expireDate, userId, card.cardDesign))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }

        get("api/cards") {
            try {
                val cardId = call.request.queryParameters["id"]!!.toString()

                call.respond(db.getCard(cardId))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }

        put("api/cards") {
            try {
                val card = call.receive<Card>()

                call.respond(db.updateCard(card.number, card.name, card.cardDesign))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }

        delete("api/cards") {
            try {
                val cardId = call.request.queryParameters["id"]!!.toString()

                call.respond(db.deleteCard(cardId))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.ExpectationFailed, e.message.toString())
            }
        }
    }

}
