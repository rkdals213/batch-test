package com.example.batchtest.batch01

import com.example.batchtest.common.ReadEntity
import com.example.batchtest.common.WriteEntity
import com.example.batchtest.common.chunkSize
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch01Reader(
    private val entityManagerFactory: EntityManagerFactory
) {
    @Bean
    @StepScope
    fun jpaPagingItemReader(): JpaPagingItemReader<ReadEntity> {
        return JpaPagingItemReaderBuilder<ReadEntity>()
            .name("batch01 jpa paging item reader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("select R from ReadEntity R")
            .build()
    }

    @Bean
    @StepScope
    fun flushTableBatch01Reader(): JpaPagingItemReader<WriteEntity> {
        return JpaPagingItemReaderBuilder<WriteEntity>()
            .name("batch01 flush table")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("select W from WriteEntity W where W.registerName = 'batch01'")
            .build()
    }
}