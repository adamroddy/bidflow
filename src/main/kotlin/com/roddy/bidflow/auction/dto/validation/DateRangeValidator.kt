package com.roddy.bidflow.auction.dto.validation

import com.roddy.bidflow.auction.dto.AuctionRequest
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DateRangeValidator::class])
annotation class ValidDateRange(
    val message: String = "endDateTime must be after startDateTime",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class DateRangeValidator : ConstraintValidator<ValidDateRange, AuctionRequest> {
    override fun isValid(dto: AuctionRequest, context: ConstraintValidatorContext): Boolean {
        return dto.endDateTime.isAfter(dto.startDateTime)
    }
}
