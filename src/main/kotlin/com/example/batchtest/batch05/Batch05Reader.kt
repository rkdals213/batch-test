package com.example.batchtest.batch05

import com.example.batchtest.common.chunkSize3
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.item.support.SynchronizedItemStreamReader
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class Batch05Reader(
    val dataSource: DataSource,
) {

    @Bean
    @StepScope
    fun readBatch05Data(): SynchronizedItemStreamReader<ReadTableDto> {
        val jdbcCursorItemReader = JdbcCursorItemReaderBuilder<ReadTableDto>()
            .name("readBatch05Data")
            .sql("select so_id, ctrt_id, cust_id, pym_acnt_id, order_tp, created_date_time from write_table")
            .rowMapper { rs, _ ->
                ReadTableDto(
                    rs.getString("so_id"),
                    rs.getString("ctrt_id"),
                    rs.getString("cust_id"),
                    rs.getString("pym_acnt_id"),
                    rs.getString("order_tp"),
                    rs.getString("created_date_time")
                )
            }
            .fetchSize(chunkSize3)
            .dataSource(dataSource)
            .build()

        return SynchronizedItemStreamReaderBuilder<ReadTableDto>()
            .delegate(jdbcCursorItemReader)
            .build()
    }
}