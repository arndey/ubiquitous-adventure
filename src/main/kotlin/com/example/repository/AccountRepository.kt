package com.example.repository

import com.example.model.Account
import java.math.BigDecimal
import java.util.*

interface AccountRepository {
    fun createAccount(initBalance: BigDecimal): Account

    fun getAccount(id: UUID): Account?
}