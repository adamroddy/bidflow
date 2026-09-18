package com.roddy.bidflow.bid.dto

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

data class BidResponse(
    val id: UUID,
    val auctionId: UUID,
    val timestamp: OffsetDateTime,
    val userId: UUID,
    val amount: BigDecimal
)
