package com.example.batchtest.batch01

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
class Batch01Step(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val batch01Reader: Batch01Reader,
    private val batch01Writer: Batch01Writer
) {

    @Bean
    @JobScope
    fun batch01Step1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return StepBuilder("batch01Step1", jobRepository)
            .chunk<WriteEntity, WriteEntity>(chunkSize1, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
            .reader(batch01Reader.readBatch01Data())
            .writer(batch01Writer.deleteBatch01Data())
            .build()
    }
}