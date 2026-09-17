package com.roddy.bidflow.bid.controller

import com.roddy.bidflow.bid.dto.BidRequest
import com.roddy.bidflow.bid.service.BidService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/bids")
class BidController(
    private val bidService: BidService
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun placeBid(@RequestBody @Valid bidRequest: BidRequest) {
        logger.info { "placeBid: {user: ${bidRequest.userId}, auction: ${bidRequest.auctionId}, amount: ${bidRequest.amount}}" }
        bidService.placeBid(bidRequest)
    }
}