package com.roddy.bidflow.media.dto

import java.util.UUID

data class MediaRequest(
    val auctionId: UUID,
    val contentType: String
)