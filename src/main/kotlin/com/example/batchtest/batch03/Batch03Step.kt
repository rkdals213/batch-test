package com.example.batchtest.batch03

import com.example.batchtest.batch01.*
import com.example.batchtest.common.*
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class Batch03Step(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val batch03Reader: Batch03Reader,
    private val batch03Writer: Batch03Writer,
    private val batch03Processor: Batch03Processor
) {

    @Bean
    @JobScope
    fun batch03Step1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return StepBuilder("batch03Step1", jobRepository)
            .chunk<ReadEntity, WriteEntity>(chunkSize2, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
            .reader(batch03Reader.readBatch03Data())
            .processor(batch03Processor.batch03Processor1())
            .writer(batch03Writer.writeBatch03Data())
            .build()
    }
}