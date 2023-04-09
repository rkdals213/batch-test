package com.example.batchtest.batch05

import com.example.batchtest.common.CustomJobListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch05(
    private val jobRepository: JobRepository,
    private val batch05Step: Batch05Step,
) {

    @Bean
    fun batch5Job(): Job {
        return JobBuilder("batch5Job", jobRepository)
            .listener(CustomJobListener())
            .start(batch05Step.batch05Step1(jobRepository))
            .build()
    }
}
