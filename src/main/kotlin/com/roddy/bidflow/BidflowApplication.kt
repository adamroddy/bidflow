package com.roddy.bidflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableCaching
@EnableScheduling
class BidflowApplication

fun main(args: Array<String>) {
	runApplication<BidflowApplication>(*args)
}
