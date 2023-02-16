package com.example.batchtest.batch01

import com.example.batchtest.common.CustomJobListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch01(
    private val jobRepository: JobRepository,
    private val batch01Step: Batch01Step
) {

    @Bean
    fun batch1Job(): Job {
        return JobBuilder("batch1Job", jobRepository)
            .listener(CustomJobListener())
            .start(batch01Step.batch01Step1(null))
            .build()
    }
}