package com.example.batchtest.batch01

import com.example.batchtest.common.ReadEntity
import com.example.batchtest.common.WriteEntity
import com.example.batchtest.common.chunkSize1
import com.example.batchtest.common.chunkSize2
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch01Reader(
    private val entityManagerFactory: EntityManagerFactory
) {
    @Bean
    @StepScope
    fun flushTableBatch01Reader1(): JpaCursorItemReader<WriteEntity> {
        return JpaCursorItemReaderBuilder<WriteEntity>()
            .name("batch01 flush table 1")
            .entityManagerFactory(entityManagerFactory)
//            .pageSize(chunkSize1)
            .queryString("select W from WriteEntity W where W.registerName = 'batch01' and W.dataType = 1 order by W.id")
            .build()
    }

    @Bean
    @StepScope
    fun jpaPagingItemReaderType1(): JpaPagingItemReader<ReadEntity> {
        return JpaPagingItemReaderBuilder<ReadEntity>()
            .name("batch01 jpa paging item reader 1")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize1)
            .queryString("select R from ReadEntity R where R.dataType = 1 order by R.id")
            .build()
    }

    @Bean
    @StepScope
    fun flushTableBatch01Reader2(): JpaCursorItemReader<WriteEntity> {
        return JpaCursorItemReaderBuilder<WriteEntity>()
            .name("batch01 flush table 2")
            .entityManagerFactory(entityManagerFactory)
//            .pageSize(chunkSize2)
            .queryString("select W from WriteEntity W where W.registerName = 'batch01' and W.dataType = 2 order by W.id")
            .build()
    }

    @Bean
    @StepScope
    fun jpaPagingItemReaderType2(): JpaPagingItemReader<ReadEntity> {
        return JpaPagingItemReaderBuilder<ReadEntity>()
            .name("batch01 jpa paging item reader 2")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize2)
            .queryString("select R from ReadEntity R where R.dataType = 2 order by R.id")
            .build()
    }
}