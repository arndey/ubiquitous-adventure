package com.example.dto.response

import com.example.dto.BigDecimalSerializer
import com.example.dto.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.*

@Serializable
data class AccountResponse(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    @Serializable(with = BigDecimalSerializer::class) val balance: BigDecimal,
)