package com.roddy.bidflow.auction.dto

import java.time.OffsetDateTime
import java.util.UUID

data class AuctionRequest(
    val userId: UUID,
    val itemId: UUID,
    val startDateTime: OffsetDateTime,
    val endDateTime: OffsetDateTime,
)
