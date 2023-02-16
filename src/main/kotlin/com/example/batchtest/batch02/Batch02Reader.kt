package com.example.batchtest.batch02

import com.example.batchtest.common.ReadEntity
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch02Reader(
    private val entityManagerFactory: EntityManagerFactory
) {

    @Bean
    @StepScope
    fun readBatch02Data(): JpaPagingItemReader<ReadEntity> {
        return JpaPagingItemReaderBuilder<ReadEntity>()
            .name("readBatch02Data")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select R from ReadEntity R where R.dataType = 1")
            .build()
    }
}