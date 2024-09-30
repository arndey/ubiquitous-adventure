package com.example.service

import java.util.*

interface LockService {
    suspend fun <T> lock(first: UUID, second: UUID, action: () -> T): T
}