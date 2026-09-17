package com.roddy.bidflow.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfig {

    @Bean
    fun auctionsTopic(): NewTopic = TopicBuilder.name("auctions").build()
}
