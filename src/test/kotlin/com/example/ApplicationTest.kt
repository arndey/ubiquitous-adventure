package com.example

import com.example.dto.request.CreateAccountRequest
import com.example.dto.request.TransferRequest
import com.example.dto.response.AccountResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*


class ApplicationTest {
    @Test
    fun `simple transfer test`(): Unit = testApplication {
        application { module() }

        val senderBalance = 1000.0
        val recipientBalance = 1000.0
        val transferAmount = 100.0

        val senderId = client.createAccount(senderBalance)
        val recipientId = client.createAccount(recipientBalance)

        client.transfer(senderId, recipientId, transferAmount)

        val sender = client.getAccount(senderId)
        val recipient = client.getAccount(recipientId)

        assertEquals(senderBalance - transferAmount, sender.balance.toDouble())
        assertEquals(recipientBalance + transferAmount, recipient.balance.toDouble())
    }

    @Test
    fun `stress transfer test`(): Unit = testApplication {
        application { module() }

        val senderBalance = 1000.0
        val recipientBalance = 1000.0
        val transferAmount = 1.0

        val senderId = client.createAccount(senderBalance)
        val recipientId = client.createAccount(recipientBalance)

        val transfersCount = 450
        val futures = mutableListOf<Deferred<HttpResponse>>()
        runBlocking {
            repeat(transfersCount) {
                async { client.transfer(senderId, recipientId, transferAmount) }.let { f -> futures.add(f) }
            }
        }
        futures.awaitAll()

        val sender = client.getAccount(senderId)
        val recipient = client.getAccount(recipientId)

        assertEquals(senderBalance - transfersCount * transferAmount, sender.balance.toDouble())
        assertEquals(recipientBalance + transfersCount * transferAmount, recipient.balance.toDouble())
    }

    private suspend fun HttpClient.createAccount(initBalance: Double): UUID {
        val createAccountRequest = CreateAccountRequest(initBalance = initBalance.toBigDecimal())
        val response = this.post("/api/account/create") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(createAccountRequest))
        }
        return UUID.fromString(response.bodyAsText())
    }

    private suspend fun HttpClient.getAccount(id: UUID): AccountResponse {
        val body = this.get("/api/account/${id}").bodyAsText()
        return Json.decodeFromString<AccountResponse>(body)
    }

    private suspend fun HttpClient.transfer(from: UUID, to: UUID, amount: Double): HttpResponse {
        val transferRequest = TransferRequest(from, to, amount = amount.toBigDecimal())

        return this.post("/api/account/transaction/transfer") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(transferRequest))
        }
    }
}