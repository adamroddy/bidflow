package com.roddy.bidflow.media.controller

import com.roddy.bidflow.media.dto.MediaRequest
import com.roddy.bidflow.media.dto.MediaResponse
import com.roddy.bidflow.media.service.MediaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/media")
class MediaController(
    private val mediaService: MediaService
) {

    @PostMapping("/upload-url")
    @ResponseStatus(HttpStatus.CREATED)
    fun generateUrl(@RequestBody mediaRequest: MediaRequest): MediaResponse {
        return mediaService.generateUrl(mediaRequest.auctionId, mediaRequest.contentType)
    }
}