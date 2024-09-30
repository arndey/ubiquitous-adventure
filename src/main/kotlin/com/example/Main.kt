package com.example

import com.example.repository.AccountRepository
import com.example.repository.impl.AccountRepositoryImpl
import com.example.service.AccountService
import com.example.service.LockService
import com.example.service.TransferService
import com.example.service.impl.AccountServiceImpl
import com.example.service.impl.LockServiceImpl
import com.example.service.impl.TransferServiceImpl
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val appComponents = module {
    single<LockService> { LockServiceImpl() }
    single<AccountRepository> { AccountRepositoryImpl() }
    single<TransferService> { TransferServiceImpl(get(), get()) }
    single<AccountService> { AccountServiceImpl(get()) }
}

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(Koin) { modules(appComponents) }
    install(ContentNegotiation) { jackson() }
    configureRouting()
}
