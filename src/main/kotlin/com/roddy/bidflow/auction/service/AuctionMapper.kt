package com.roddy.bidflow.auction.service

import com.roddy.bidflow.auction.domain.Auction
import com.roddy.bidflow.auction.domain.AuctionStatus
import com.roddy.bidflow.auction.dto.AuctionRequest
import com.roddy.bidflow.auction.dto.AuctionResponse
import org.springframework.stereotype.Component

@Component
class AuctionMapper {

    fun buildAuction(request: AuctionRequest): Auction {
        return Auction(
            itemId = request.itemId,
            userId = request.userId,
            startDateTime = request.startDateTime,
            endDateTime = request.endDateTime,
            status = AuctionStatus.PENDING
        )
    }

    fun buildAuctionResponse(auction: Auction): AuctionResponse {
        val auctionId = auction.id ?: throw IllegalStateException("Boom!")
        return AuctionResponse(
            id = auctionId,
            itemId = auction.itemId,
            userId = auction.userId,
            currentBidId = auction.currentBidId,
            currentAmount = auction.currentAmount,
            startDateTime = auction.startDateTime,
            endDateTime = auction.endDateTime,
            status = auction.status
        )
    }
}
