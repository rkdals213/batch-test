package com.example.batchtest.batch01

import com.example.batchtest.common.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    private val jobParameter: CustomJobParameter
) {

    private val logger: Logger = LoggerFactory.getLogger(Batch01Step::class.java)

    @Bean
    @JobScope
    fun batch01Step1(): Step {
        logger.info("############################ ${jobParameter.requestDate}")

        return StepBuilder("batch01Step1", jobRepository)
            .chunk<WriteEntity, WriteEntity>(chunkSize1, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
            .reader(batch01Reader.readBatch01Data())
            .writer(batch01Writer.deleteBatch01Data())
            .build()
    }
}