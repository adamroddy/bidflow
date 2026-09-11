package com.roddy.bidflow.auction.service

import com.roddy.bidflow.auction.repository.AuctionRepository
import com.roddy.bidflow.auction.dto.AuctionRequest
import com.roddy.bidflow.auction.dto.AuctionResponse
import org.springframework.stereotype.Service

@Service
class AuctionService(
    private val auctionMapper: AuctionMapper,
    private val auctionRepository: AuctionRepository
) {

    fun create(request: AuctionRequest): AuctionResponse {
        val auction = auctionMapper.build(request)
        val record =  auctionRepository.save(auction)
        return auctionMapper.build(record)
    }
}
