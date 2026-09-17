package com.roddy.bidflow.exception

import com.roddy.bidflow.auction.domain.AuctionStatus
import com.roddy.bidflow.auction.dto.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID

@RestControllerAdvice
class GlobalExceptionHandler {

    val logger = KotlinLogging.logger {}

    companion object {
        const val VALIDATION_ERROR_MESSAGE = "Validation failed"
        const val BID_ON_INACTIVE_AUCTION_ERROR_MESSAGE = "Bid on inactive auction"
        const val BID_AMOUNT_TOO_LOW_ERROR_MESSAGE = "Bid amount too low"
        const val SELF_BIDDING_ERROR_MESSAGE = "Self bidding not allowed"
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val errorBody = ErrorResponse(
            errorCode = status.reasonPhrase,
            errors = exception.bindingResult.allErrors.associate {
                ((it as? FieldError)?.field ?: it.objectName) to it.defaultMessage
            },
            message = VALIDATION_ERROR_MESSAGE,
            timestamp = OffsetDateTime.now(ZoneOffset.UTC)
        )
        handleErrors(errorBody)
        return ResponseEntity(errorBody, status)
    }

    fun handleErrors(errorBody: ErrorResponse) {
        logger.info { "Invalid request: ${errorBody.errors}" }
    }

    @ExceptionHandler(BidOnInactiveAuctionException::class)
    fun handleBidOnInactiveAuctionException(exception: BidOnInactiveAuctionException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val errorBody = ErrorResponse(
            errorCode = status.reasonPhrase,
            message = "Auction ${exception.auctionId} with current status ${exception.status} cannot accept bids until it is ACTIVE.",
            timestamp = OffsetDateTime.now(ZoneOffset.UTC)
        )
        handleErrors(errorBody)
        return ResponseEntity(errorBody, status)
    }

    @ExceptionHandler(BidAmountTooLowException::class)
    fun handleBidAmountTooLowException(exception: BidAmountTooLowException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val errorBody = ErrorResponse(
            errorCode = status.reasonPhrase,
            message = "Bid of ${exception.attemptedAmount} is too low. The current bid is ${exception.currentAmount}.",
            timestamp = OffsetDateTime.now(ZoneOffset.UTC)
        )
        handleErrors(errorBody)
        return ResponseEntity(errorBody, status)
    }

    @ExceptionHandler(SelfBiddingException::class)
    fun handleSelfBiddingException(exception: SelfBiddingException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val errorBody = ErrorResponse(
            errorCode = status.reasonPhrase,
            message = "User ${exception.userId} is not allowed to bid on their own auction ${exception.auctionId}.",
            timestamp = OffsetDateTime.now(ZoneOffset.UTC)
        )
        handleErrors(errorBody)
        return ResponseEntity(errorBody, status)
    }
}

class BidOnInactiveAuctionException(val auctionId: UUID, val status: AuctionStatus) : RuntimeException()

class BidAmountTooLowException(val attemptedAmount: BigDecimal, val currentAmount: BigDecimal) : RuntimeException()

class SelfBiddingException(val userId: UUID, val auctionId: UUID) : RuntimeException()