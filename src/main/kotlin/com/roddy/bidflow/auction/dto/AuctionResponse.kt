package com.roddy.bidflow.auction.dto

import com.roddy.bidflow.auction.domain.AuctionState
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

data class AuctionResponse(
    val id: UUID,
    val itemId: UUID,
    val userId: UUID,
    val currentBidId: UUID? = null,
    val currentAmount: BigDecimal? = null,
    val startDateTime: OffsetDateTime,
    val endDateTime: OffsetDateTime,
    val state: AuctionState
)
