package com.example.dto.request

import com.example.dto.BigDecimalSerializer
import com.example.dto.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.*

@Serializable
data class TransferRequest(
    @Serializable(with = UUIDSerializer::class) val from: UUID,
    @Serializable(with = UUIDSerializer::class) val to: UUID,
    @Serializable(with = BigDecimalSerializer::class) val amount: BigDecimal,
)
