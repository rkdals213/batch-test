package com.example.batchtest.batch03

import com.example.batchtest.common.ReadEntity
import com.example.batchtest.common.WriteEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch03Processor(
    private val logger: Logger = LoggerFactory.getLogger(Batch03Processor::class.java)
) {

    @Bean
    fun batch03Processor1(): ItemProcessor<ReadEntity, WriteEntity> {
        return ItemProcessor<ReadEntity, WriteEntity> {
            logger.info("Convert ReadEntity to WriteEntity")

//            if (it.readData == "data8") {
//                throw RuntimeException("data8")
//            }

            WriteEntity(readData = it.readData, registerName = "Batch03", dataType = it.dataType)
        }
    }
}