package com.roddy.bidflow.bid.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.io.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class"
)
data class BidResponse(
    val id: UUID,
    val auctionId: UUID,
    val timestamp: OffsetDateTime,
    val userId: UUID,
    val amount: BigDecimal
) : Serializable
