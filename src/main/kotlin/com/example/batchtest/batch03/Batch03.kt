package com.example.batchtest.batch03

import com.example.batchtest.common.CustomJobListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch03(
    private val jobRepository: JobRepository,
    private val batch03Step: Batch03Step
) {
    @Bean
    fun batch3Job(): Job {
        return JobBuilder("batch3Job", jobRepository)
            .listener(CustomJobListener())
            .start(batch03Step.batch03Step1(null))
            .build()
    }
}
