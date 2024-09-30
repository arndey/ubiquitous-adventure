package com.example

import com.example.dto.request.CreateAccountRequest
import com.example.dto.request.TransferRequest
import com.example.service.AccountService
import com.example.service.TransferService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import java.util.UUID

fun Application.configureRouting() {
    val transferService: TransferService by inject()
    val accountService: AccountService by inject()

    routing {
        route("/api/account") {
            get("/{id}") {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid account ID")
                    return@get
                }

                when (val account = accountService.getAccount(id)) {
                    null -> call.respond(HttpStatusCode.NotFound, "Account not found")
                    else -> call.respond(account)
                }
            }

            post("/create") {
                val request = call.receive<CreateAccountRequest>()
                val account = accountService.createAccount(request.initBalance)
                call.respond(account.id.toString())
            }

            route("/transaction") {
                post("/transfer") {
                    val request = call.receive<TransferRequest>()
                    transferService.transfer(request.from, request.to, request.amount)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}
