package com.roddy.bidflow.media.service

import com.roddy.bidflow.config.MinioConfig
import com.roddy.bidflow.media.dto.MediaResponse
import io.minio.GetPresignedObjectUrlArgs
import io.minio.Http
import io.minio.MinioClient
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.TimeUnit

@Service
class MediaService(
    private val minioConfig: MinioConfig,
    private val minioClient: MinioClient
) {

    fun generateUrl(auctionId: UUID, contentType: String): MediaResponse {
        val uuid = UUID.randomUUID()
        val objectKey = "auctions/$auctionId/$uuid"
        val url = minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Http.Method.PUT)
                .`object`(objectKey)
                .expiry(1, TimeUnit.HOURS)
                .bucket(minioConfig.bucketName)
                .extraQueryParams(mapOf("Content-Type" to contentType))
                .build()
        )

        return MediaResponse(url = url, objectKey = objectKey)
    }
}