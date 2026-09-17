package com.roddy.bidflow.bid.service

import com.datastax.oss.driver.api.core.uuid.Uuids
import com.roddy.bidflow.bid.domain.Bid
import com.roddy.bidflow.bid.dto.BidRequest
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class BidMapper {

    fun buildBid(bidRequest: BidRequest): Bid {
        return Bid(
            auctionId = bidRequest.auctionId,
            timestamp = Instant.now(),
            id = Uuids.timeBased(),
            userId = bidRequest.userId,
            amount = bidRequest.amount
        )
    }
}