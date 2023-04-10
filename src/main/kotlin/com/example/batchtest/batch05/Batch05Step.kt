package com.example.batchtest.batch05

import com.example.batchtest.common.CustomChunkListener
import com.example.batchtest.common.CustomStepExecutionListener
import com.example.batchtest.common.chunkSize3
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class Batch05Step(
    private val transactionManager: PlatformTransactionManager,
    private val batch05Reader: Batch05Reader,
    private val batch05Writer: Batch05Writer,
    private val jdbcTemplate: JdbcTemplate,
) {

    @Bean
    @JobScope
    fun batch05Step1(jobRepository: JobRepository): Step {
        return StepBuilder("batch05Step1", jobRepository)
            .tasklet(tasklet(), transactionManager)
//            .chunk<ReadTableDto, ReadTableDto>(chunkSize3, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
//            .reader(batch05Reader.readBatch05Data())
//            .writer(batch05Writer.deleteBatch05Data())
//            .taskExecutor(executor())
            .build()
    }

    private fun tasklet(): Tasklet {
        return Tasklet { contribution: StepContribution, chunkContext: ChunkContext ->
            jdbcTemplate.update("call updateData()")
            RepeatStatus.FINISHED
        }
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