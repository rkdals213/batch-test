package com.example.batchtest.batch01

import com.example.batchtest.common.WriteEntity
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch01Reader(
    private val entityManagerFactory: EntityManagerFactory
) {
    @Bean
    @StepScope
    fun readBatch01Data(): JpaCursorItemReader<WriteEntity> {
        return JpaCursorItemReaderBuilder<WriteEntity>()
            .name("readBatch01Data")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select W from WriteEntity W")
            .build()
    }
}