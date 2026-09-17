package com.roddy.bidflow.outbox

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class OutboxPoller(
    private val outboxRepository: OutboxRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    private val logger = KotlinLogging.logger {}

    @Scheduled(fixedDelay = 500)
    @Transactional
    fun pollAndPublish() {
        val pendingRows = outboxRepository.findTop50ByProcessedAtIsNullOrderByCreatedAtAsc()

        if (pendingRows.isEmpty()) return

        logger.info { "Processing ${pendingRows.size} outbox events from Postgres" }

        for (event in pendingRows) {
            try {
                kafkaTemplate.send(
                    event.aggregateType,
                    event.aggregateId,
                    event.payload
                ).get()

                event.processedAt = OffsetDateTime.now()

            } catch (e: Exception) {
                logger.error { "Failed to stream event ${event.id} to Kafka. Rolling back transaction. Exception: $e" }
            }
        }

        outboxRepository.saveAll(pendingRows)
    }
}