package com.example.batchtest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
@ConfigurationPropertiesScan
class BatchTestApplication

fun main(args: Array<String>) {
    val exitCode = try {
        SpringApplication.exit(runApplication<BatchTestApplication>(*args))
    } catch (e: Exception) {
        5
    }

    exitProcess(exitCode)
}
