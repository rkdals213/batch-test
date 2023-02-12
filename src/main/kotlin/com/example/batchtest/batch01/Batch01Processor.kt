package com.example.batchtest.batch01

import com.example.batchtest.common.ReadEntity
import com.example.batchtest.common.WriteEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch01Processor(
    private val logger: Logger = LoggerFactory.getLogger(Batch01Processor::class.java)
) {

    @Bean
    fun processor(): ItemProcessor<ReadEntity, WriteEntity> {
        return ItemProcessor<ReadEntity, WriteEntity> {
            logger.info("Convert ReadEntity to WriteEntity")
            WriteEntity(readData = it.readData, registerName = "Batch01")
        }
    }
}