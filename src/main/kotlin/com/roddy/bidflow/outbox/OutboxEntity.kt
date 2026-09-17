package com.roddy.bidflow.outbox

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "outbox")
class OutboxEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "aggregate_type", nullable = false)
    val aggregateType: String,

    @Column(name = "aggregate_id", nullable = false)
    val aggregateId: String,

    @Column(name = "event_type", nullable = false)
    val eventType: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    val payload: String,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "processed_at")
    var processedAt: OffsetDateTime? = null
)