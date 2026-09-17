package com.roddy.bidflow.auction.dto

import com.roddy.bidflow.auction.dto.validation.ValidDateRange
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

@ValidDateRange
data class AuctionRequest(
    @NotNull
    val userId: UUID,
    @NotNull
    val itemId: UUID,
    @NotNull
    @Future
    val startDateTime: OffsetDateTime,
    @NotNull
    @Future
    val endDateTime: OffsetDateTime,

)
