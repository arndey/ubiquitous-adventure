package com.example.repository.impl

import com.example.model.Account
import com.example.repository.AccountRepository
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class AccountRepositoryImpl : AccountRepository {

    private val accounts = ConcurrentHashMap<UUID, Account>()

    override fun createAccount(initBalance: BigDecimal): Account {
        val account = Account(UUID.randomUUID(), initBalance)
        accounts[account.id] = account
        return account
    }

    override fun getAccount(id: UUID): Account? {
        return accounts.getOrElse(id) { null }
    }
}