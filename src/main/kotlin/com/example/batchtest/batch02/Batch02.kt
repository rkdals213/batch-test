package com.example.batchtest.batch02

import com.example.batchtest.common.CustomJobListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch02(
    private val jobRepository: JobRepository,
    private val batch02Step: Batch02Step
) {
    @Bean
    fun batch2Job(): Job {
        return JobBuilder("batch2Job", jobRepository)
            .listener(CustomJobListener())
            .start(batch02Step.batch02Step1())
            .build()
    }
}
