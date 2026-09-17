package com.roddy.bidflow.auction.service

import com.roddy.bidflow.auction.repository.AuctionRepository
import com.roddy.bidflow.auction.dto.AuctionRequest
import com.roddy.bidflow.auction.dto.AuctionResponse
import com.roddy.bidflow.outbox.OutboxEntityMapper
import com.roddy.bidflow.outbox.OutboxRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import tools.jackson.databind.ObjectMapper
import java.time.Duration
import java.util.UUID

@Service
class AuctionService(
    private val auctionMapper: AuctionMapper,
    private val auctionRepository: AuctionRepository,
    private val outboxRepository: OutboxRepository,
    private val outboxEntityMapper: OutboxEntityMapper,
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) {

    private val logger = KotlinLogging.logger {}

    @Transactional
    fun createAuction(request: AuctionRequest): AuctionResponse {
        val auction = auctionMapper.buildAuction(request)
        logger.info { "Inserting auction: $auction" }
        val record = auctionRepository.save(auction)
        val outboxEntry = outboxEntityMapper.buildOutboxEntity(record)
        outboxRepository.save(outboxEntry)
        return auctionMapper.buildAuctionResponse(record)
    }

    fun findAuctionById(auctionId: UUID): AuctionResponse {
        logger.info { "Fetching auction: {id: $auctionId}" }
        val cacheKey = "auction::$auctionId"

        redisTemplate.opsForValue().get(cacheKey)?.let {
            logger.info { "Cache hit for auction: $auctionId" }
            return objectMapper.readValue(it, AuctionResponse::class.java)
        }

        logger.info { "Cache miss for auction: $auctionId, fetching from DB" }
        val auction = auctionRepository.findByIdOrNull(auctionId)
            .also { if (it == null) logger.info { "Could not find auction with id: $auctionId" } }
            ?.let { auctionMapper.buildAuctionResponse(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found")

        redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(auction), Duration.ofMinutes(15))

        return auction
    }
}
