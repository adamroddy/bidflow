package com.roddy.bidflow.bid.service

import com.roddy.bidflow.auction.domain.AuctionStatus
import com.roddy.bidflow.auction.service.AuctionService
import com.roddy.bidflow.bid.domain.Bid
import com.roddy.bidflow.bid.dto.BidRequest
import com.roddy.bidflow.bid.repository.BidRepository
import com.roddy.bidflow.exception.BidAmountTooLowException
import com.roddy.bidflow.exception.BidOnInactiveAuctionException
import com.roddy.bidflow.exception.SelfBiddingException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class BidService(
    private val bidRepository: BidRepository,
    private val auctionService: AuctionService,
    private val bidMapper: BidMapper
) {

    private val logger = KotlinLogging.logger {}

    /**
     * Bids can only be placed on active auctions
     * A bid must exceed the current highest bid
     * A user cannot bid on their own auction
     */
    @Transactional
    fun placeBid(bidRequest: BidRequest): Bid? {
        val auction = auctionService.findAuctionById(bidRequest.auctionId)
        val auctionId = auction.id
        return when {
            auction.status != AuctionStatus.ACTIVE -> {
                logger.info { "Bid for inactive auction: {user: ${bidRequest.userId}, auction: ${bidRequest.auctionId}, status: ${auction.status}}" }
                throw BidOnInactiveAuctionException(bidRequest.auctionId, auction.status)
            }
            bidRequest.amount < auction.currentAmount -> {
                logger.info { "Bid too low: {auction: ${bidRequest.auctionId}, current auction amount=${auction.currentAmount}, bid amount: ${bidRequest.amount}}" }
                throw BidAmountTooLowException(bidRequest.amount, auction.currentAmount)
            }
            bidRequest.userId == auction.userId -> {
                logger.info { "Bid on own auction: {user: ${bidRequest.userId}, auction: $auctionId}" }
                throw SelfBiddingException(bidRequest.userId, auctionId)
            }
            else -> {
                val bid = bidMapper.buildBid(bidRequest)
                logger.info { "Placing bid: {user: ${bid.userId}, auction: $auctionId, amount: ${bid.amount}}" }
                bidRepository.save(bid)
            }
        }
    }


    fun finByAuctionId(auctionId: UUID): List<Bid> {
        return bidRepository.findByAuctionId(auctionId)
    }
}