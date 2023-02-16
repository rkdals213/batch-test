package com.example.batchtest.batch02

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
class Batch02Step(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val batch02Reader: Batch02Reader,
    private val batch02Writer: Batch02Writer,
    private val batch02Processor: Batch02Processor
) {

    @Bean
    @JobScope
    fun batch02Step1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return StepBuilder("batch02Step1", jobRepository)
            .chunk<ReadEntity, WriteEntity>(chunkSize1, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
            .reader(batch02Reader.readBatch02Data())
            .processor(batch02Processor.batch02Processor1())
            .writer(batch02Writer.writeBatch02Data())
            .build()
    }
}