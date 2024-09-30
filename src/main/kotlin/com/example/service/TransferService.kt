package com.example.service

import java.math.BigDecimal
import java.util.*

interface TransferService {
    suspend fun transfer(from: UUID, to: UUID, amount: BigDecimal)
}