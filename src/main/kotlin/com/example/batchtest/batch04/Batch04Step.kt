package com.example.batchtest.batch04

import com.example.batchtest.common.CustomChunkListener
import com.example.batchtest.common.CustomStepExecutionListener
import com.example.batchtest.common.WriteEntity
import com.example.batchtest.common.chunkSize1
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class Batch04Step(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val batch04Reader: Batch04Reader,
    private val batch04Writer: Batch04Writer
) {

    @Bean
    @JobScope
    fun batch04Step1(): Step {
        return StepBuilder("batch04Step1", jobRepository)
            .chunk<WriteEntity, WriteEntity>(chunkSize1, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
            .reader(batch04Reader.readBatch04Data())
            .writer(batch04Writer.deleteBatch04Data())
            .build()
    }
}