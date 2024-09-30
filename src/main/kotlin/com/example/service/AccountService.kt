package com.example.service

import com.example.dto.response.AccountResponse
import java.math.BigDecimal
import java.util.*

interface AccountService {
    fun createAccount(initBalance: BigDecimal): AccountResponse

    fun getAccount(id: UUID): AccountResponse?
}