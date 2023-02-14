package com.example.batchtest.batch01

import com.example.batchtest.common.ReadEntity
import com.example.batchtest.common.WriteEntity
import com.example.batchtest.common.chunkSize1
import com.example.batchtest.common.chunkSize2
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
    fun batch01FlushStep1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        println("############################ batch01FlushStep1 Start")
        println("############################ $requestDate")

        return StepBuilder("batch01FlushStep1", jobRepository)
            .chunk<WriteEntity, WriteEntity>(chunkSize1, transactionManager)
            .reader(batch01Reader.flushTableBatch01Reader1())
            .writer(batch01Writer.flushTableBatch01Writer())
            .build()
    }

    @Bean
    @JobScope
    fun batch1Step1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        println("############################ batch1Step1 Start")
        println("############################ $requestDate")

        return StepBuilder("batch1Step1", jobRepository)
            .chunk<ReadEntity, WriteEntity>(chunkSize1, transactionManager)
            .reader(batch01Reader.jpaPagingItemReaderType1())
            .processor(batch01Processor.processor())
            .writer(batch01Writer.jpaPagingItemWriter())
            .build()
    }

    @Bean
    @JobScope
    fun batch01FlushStep2(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        println("############################ batch01FlushStep2 Start")
        println("############################ $requestDate")

        return StepBuilder("batch01FlushStep2", jobRepository)
            .chunk<WriteEntity, WriteEntity>(chunkSize2, transactionManager)
            .reader(batch01Reader.flushTableBatch01Reader2())
            .writer(batch01Writer.flushTableBatch01Writer())
            .build()
    }

    @Bean
    @JobScope
    fun batch1Step2(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        println("############################ batch1Step2 Start")
        println("############################ $requestDate")

        return StepBuilder("batch1Step2", jobRepository)
            .chunk<ReadEntity, WriteEntity>(chunkSize2, transactionManager)
            .reader(batch01Reader.jpaPagingItemReaderType2())
            .processor(batch01Processor.processor())
            .writer(batch01Writer.jpaPagingItemWriter())
            .build()
    }
}