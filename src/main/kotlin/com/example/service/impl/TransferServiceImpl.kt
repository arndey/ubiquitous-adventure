package com.example.service.impl

import com.example.repository.AccountRepository
import com.example.service.LockService
import com.example.service.TransferService
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import java.math.BigDecimal
import java.util.*

class TransferServiceImpl(
    private val lockService: LockService,
    private val accountRepository: AccountRepository
) : TransferService {
    override suspend fun transfer(from: UUID, to: UUID, amount: BigDecimal) {
        if (amount < BigDecimal.ZERO) throw BadRequestException("Transfer amount must be greater than zero")

        lockService.lock(from, to) {
            val fromAccount = accountRepository.getAccount(from)
                ?: throw NotFoundException("Sender not found by ID#${from}")
            val toAccount = accountRepository.getAccount(to)
                ?: throw NotFoundException("Recipient not found by ID#${to}")

            if (fromAccount.balance < amount) {
                throw BadRequestException("Insufficient balance in account ${fromAccount.id}")
            }
            fromAccount.balance -= amount
            toAccount.balance += amount
        }
    }
}
