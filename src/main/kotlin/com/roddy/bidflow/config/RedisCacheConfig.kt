package com.roddy.bidflow.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import tools.jackson.databind.DefaultTyping
import tools.jackson.databind.json.JsonMapper
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import tools.jackson.module.kotlin.kotlinModule
import java.time.Duration

@Configuration
@EnableCaching
class RedisCacheConfig {

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {

        val secureValidator = BasicPolymorphicTypeValidator.builder()
            .allowIfBaseType("com.roddy.bidflow")
            .allowIfBaseType("java.math")
            .build()

        val cacheJsonMapper = JsonMapper.builder()
            .addModule(kotlinModule())
            .activateDefaultTyping(
                secureValidator,
                DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
            )
            .build()

        val jsonSerializer = GenericJacksonJsonRedisSerializer(cacheJsonMapper)

        val defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(15L))
            .disableCachingNullValues()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer)
            )

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultCacheConfig)
            .build()
    }
}
