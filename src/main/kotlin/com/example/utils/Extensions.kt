package com.example.utils

import com.example.dto.response.AccountResponse
import com.example.model.Account

fun Account.toResponse() = AccountResponse(
    id = this.id,
    balance = this.balance,
)