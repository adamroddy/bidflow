package com.roddy.bidflow.outbox

import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import java.util.UUID

interface OutboxRepository : JpaRepository<OutboxEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(
        QueryHint(name = "jakarta.persistence.lock.timeout", value = "-2")
    )
    fun findTop50ByProcessedAtIsNullOrderByCreatedAtAsc(): List<OutboxEntity>
}
