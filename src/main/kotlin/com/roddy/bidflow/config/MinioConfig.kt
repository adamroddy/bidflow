package com.roddy.bidflow.config

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig {

    @Value("\${spring.minio.endpoint}")
    val endpoint: String? = null

    @Value("\${spring.minio.accesskey}")
    val accessKey: String? = null

    @Value("\${spring.minio.secretkey}")
    val secretKey: String? = null

    @Value("\${spring.minio.bucketname}")
    val bucketName: String? = null

    @Bean
    fun minioClient(): MinioClient {
        // "auctions/$auctionId/$uuid"
        return MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build()
    }
}