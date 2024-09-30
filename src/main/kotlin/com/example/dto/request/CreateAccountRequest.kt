package com.example.dto.request

import com.example.dto.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CreateAccountRequest(
    @Serializable(with = BigDecimalSerializer::class) val initBalance: BigDecimal,
)