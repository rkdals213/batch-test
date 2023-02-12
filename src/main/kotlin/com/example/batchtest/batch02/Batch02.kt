package com.example.batchtest.batch02

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class Batch02(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {

    @Bean
    fun batch2Job(): Job {
        return JobBuilder("batch2job", jobRepository)
            .start(batch2Step1())
            .build()
    }

    @Bean
    fun batch2Step1(): Step {
        return StepBuilder("batch2step1", jobRepository)
            .tasklet(batch2tasklet(null), transactionManager)
            .build()

    }

    @Bean
    @StepScope
    fun batch2tasklet(@Value("#{jobParameters[requestDate]}") requestDate: String?): Tasklet {
        return Tasklet { _: StepContribution, _: ChunkContext ->
            println("############################ $requestDate")
            if (requestDate!!.take(12).toLong() % 2 == 0L) {
                throw RuntimeException()
            }
            RepeatStatus.FINISHED
        }
    }
}
