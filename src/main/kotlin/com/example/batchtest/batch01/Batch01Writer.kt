package com.example.batchtest.batch01

import com.example.batchtest.common.WriteEntity
import com.example.batchtest.common.WriteRepository
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Batch01Writer(
    private val writeRepository: WriteRepository
) {

    @Bean
    @StepScope
    fun deleteBatch01Data(): ItemWriter<WriteEntity> {
        return ItemWriter<WriteEntity> { list: Chunk<out WriteEntity> ->
            writeRepository.deleteAllInBatch(list)
        }
    }
}