package com.example.service.impl

import com.example.service.LockService
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class LockServiceImpl : LockService {

    private val lockMap: ConcurrentHashMap<UUID, Mutex> = ConcurrentHashMap()

    private fun getLock(id: UUID): Mutex {
        return lockMap.computeIfAbsent(id) { Mutex() }
    }

    override suspend fun <T> lock(first: UUID, second: UUID, action: () -> T): T {
        val (firstLock, secondLock) = when (first < second) {
            true -> getLock(first) to getLock(second)
            false -> getLock(second) to getLock(first)
        }
        return firstLock.withLock {
            secondLock.withLock {
                action()
            }
        }
    }
}