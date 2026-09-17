package com.roddy.bidflow.auction.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.OffsetDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val errorCode: String,
    val errors: Map<String, String?>? = null,
    val message: String?,
    val timestamp: OffsetDateTime = OffsetDateTime.now()
)
