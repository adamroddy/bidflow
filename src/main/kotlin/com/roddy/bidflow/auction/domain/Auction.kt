package com.roddy.bidflow.auction.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "auction")
class Auction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    val itemId: UUID,

    @Column(nullable = false)
    val userId: UUID,

    @Column
    val currentBidId: UUID? = null,

    @Column
    val currentAmount: BigDecimal? = null,

    @Column(nullable = false)
    val startDateTime: OffsetDateTime,

    @Column(nullable = false)
    val endDateTime: OffsetDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val state: AuctionState
)
