package com.example.batchtest.batch05

import com.example.batchtest.common.CustomChunkListener
import com.example.batchtest.common.CustomStepExecutionListener
import com.example.batchtest.common.chunkSize3
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class Batch05Step(
    private val transactionManager: PlatformTransactionManager,
    private val batch05Reader: Batch05Reader,
    private val batch05Writer: Batch05Writer,
) {

    @Bean
    @JobScope
    fun batch05Step1(jobRepository: JobRepository): Step {
        return StepBuilder("batch05Step1", jobRepository)
            .chunk<ReadTableDto, ReadTableDto>(chunkSize3, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
            .reader(batch05Reader.readBatch05Data())
            .writer(batch05Writer.deleteBatch05Data())
            .taskExecutor(executor())
            .build()
    }

    fun executor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 10
        executor.setThreadNamePrefix("multi-thread-")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }
}