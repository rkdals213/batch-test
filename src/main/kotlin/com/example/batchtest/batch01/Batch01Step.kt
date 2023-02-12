package com.example.batchtest.batch01

import com.example.batchtest.common.ReadEntity
import com.example.batchtest.common.WriteEntity
import com.example.batchtest.common.chunkSize
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class Batch01Step(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val batch01Reader: Batch01Reader,
    private val batch01Writer: Batch01Writer,
    private val batch01Processor: Batch01Processor
) {

    @Bean
    @JobScope
    fun batch1Step1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        println("############################ batch1Step1 Start")
        println("############################ $requestDate")

        return StepBuilder("batch1Step1", jobRepository)
            .chunk<ReadEntity, WriteEntity>(chunkSize, transactionManager)
            .reader(batch01Reader.jpaPagingItemReader())
            .processor(batch01Processor.processor())
            .writer(batch01Writer.jpaPagingItemWriter())
            .build()
    }

    @Bean
    @JobScope
    fun flushTableBatch01(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        println("############################ flushTableBatch01 Start")
        println("############################ $requestDate")

        return StepBuilder("flushTableBatch01", jobRepository)
            .chunk<WriteEntity, WriteEntity>(chunkSize, transactionManager)
            .reader(batch01Reader.flushTableBatch01Reader())
            .writer(batch01Writer.flushTableBatch01Writer())
            .build()
    }
}