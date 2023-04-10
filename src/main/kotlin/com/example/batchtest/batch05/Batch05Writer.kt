package com.example.batchtest.batch05

import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class Batch05Writer(
    private val dataSource: DataSource,
) {

    @Bean
    @StepScope
    fun deleteBatch05Data(): JdbcBatchItemWriter<ReadTableDto> {
        return JdbcBatchItemWriterBuilder<ReadTableDto>()
            .dataSource(dataSource)
            .sql("insert into write_table (so_id, ctrt_id, cust_id, pym_acnt_id, order_tp, created_date_time) values (:soId, :ctrtId, :custId, :pymAcntId, :orderTp, :createdDateTime)")
            .beanMapped()
            .build()
    }
}