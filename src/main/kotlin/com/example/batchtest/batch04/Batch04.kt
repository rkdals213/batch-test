package com.example.batchtest.batch04

import com.example.batchtest.common.CustomJobListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch04(
    private val jobRepository: JobRepository,
    private val batch04Step: Batch04Step
) {

    @Bean
    fun batch4Job(): Job {
        return JobBuilder("batch4Job", jobRepository)
            .listener(CustomJobListener())
            .start(batch04Step.batch04Step1())
            .build()
    }
}