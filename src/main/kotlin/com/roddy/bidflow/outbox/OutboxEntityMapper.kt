package com.roddy.bidflow.outbox

import com.roddy.bidflow.auction.domain.Auction
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import tools.jackson.databind.ObjectMapper

@Component
class OutboxEntityMapper(
    private val objectMapper: ObjectMapper
) {

    fun buildOutboxEntity(auction: Auction): OutboxEntity {
        val recordId = auction.id ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found")

        val eventPayload = AuctionEvent(
            auctionId = recordId,
            itemId = auction.itemId,
            userId = auction.userId,
            startDateTime = auction.startDateTime,
            endDateTime = auction.endDateTime,
            status = auction.status
        )

        val payloadJson = objectMapper.writeValueAsString(eventPayload)
        return OutboxEntity(
            aggregateType = "auctions",
            aggregateId = auction.id.toString(),
            eventType = "AuctionCreated",
            payload = payloadJson
        )
    }
}