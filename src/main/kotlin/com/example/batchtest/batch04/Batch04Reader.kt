package com.example.batchtest.batch04

import com.example.batchtest.common.WriteEntity
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch04Reader(
    private val entityManagerFactory: EntityManagerFactory
) {
    @Bean
    @StepScope
    fun readBatch04Data(): JpaCursorItemReader<WriteEntity> {
        return JpaCursorItemReaderBuilder<WriteEntity>()
            .name("readBatch04Data")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select W from WriteEntity W where W.registerName = 'Batch02'")
            .build()
    }
}