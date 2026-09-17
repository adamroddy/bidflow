package com.roddy.bidflow.bid.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

data class BidRequest(
    @NotNull
    val auctionId: UUID,
    @NotNull
    val userId: UUID,
    @NotNull
    @Positive
    val amount: BigDecimal
)
