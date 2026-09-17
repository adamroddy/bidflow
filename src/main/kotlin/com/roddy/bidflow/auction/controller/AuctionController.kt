package com.roddy.bidflow.auction.controller

import com.roddy.bidflow.auction.dto.AuctionRequest
import com.roddy.bidflow.auction.dto.AuctionResponse
import com.roddy.bidflow.auction.service.AuctionService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Validated
@RequestMapping("/auctions")
class AuctionController(
    private val auctionService: AuctionService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAuction(@RequestBody @Valid auction: AuctionRequest): AuctionResponse {
        logger.info { "createAuction: {user: ${auction.userId}, item: ${auction.itemId}}" }
        return auctionService.createAuction(auction)
    }

    @GetMapping("/{id}")
    fun findAuctionById(@PathVariable("id") auctionId: UUID): AuctionResponse {
        logger.info { "findAuctionById: {auctionId: $auctionId}" }
        return auctionService.findAuctionById(auctionId)
    }
}
