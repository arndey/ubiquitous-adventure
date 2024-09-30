package com.example.model

import java.math.BigDecimal
import java.util.*

data class Account(
    val id: UUID,
    var balance: BigDecimal,
)
