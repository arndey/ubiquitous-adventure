package com.example.service.impl

import com.example.dto.response.AccountResponse
import com.example.repository.AccountRepository
import com.example.service.AccountService
import com.example.utils.toResponse
import io.ktor.server.plugins.BadRequestException
import java.math.BigDecimal
import java.util.*

class AccountServiceImpl(private val accountRepository: AccountRepository) : AccountService {
    override fun createAccount(initBalance: BigDecimal): AccountResponse {
        if (initBalance < BigDecimal.ZERO) throw BadRequestException("initBalance must be positive")

        return accountRepository.createAccount(initBalance).toResponse()
    }

    override fun getAccount(id: UUID): AccountResponse? {
        return accountRepository.getAccount(id)?.toResponse()
    }
}