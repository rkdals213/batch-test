package com.example.batchtest.batch01

import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class Batch01(
    private val jobRepository: JobRepository,
    private val batch01Step: Batch01Step
) {

    @Bean
    fun batch1Job(): Job {
        return JobBuilder("batch1job", jobRepository)
            .start(batch01Step.flushTableBatch01(null))
            .next(batch01Step.batch1Step1(null))
            .build()
    }
}