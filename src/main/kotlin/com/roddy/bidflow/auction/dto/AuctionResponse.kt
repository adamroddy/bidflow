package com.roddy.bidflow.auction.dto

import com.roddy.bidflow.auction.domain.AuctionStatus
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

data class AuctionResponse(
    val id: UUID,
    val itemId: UUID,
    val userId: UUID,
    val currentBidId: UUID? = null,
    val currentAmount: BigDecimal = BigDecimal.ZERO,
    val startDateTime: OffsetDateTime,
    val endDateTime: OffsetDateTime,
    val status: AuctionStatus
)
