package com.example.batchtest

import com.example.batchtest.batch05.ReadTableRepository
import jakarta.annotation.PostConstruct
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowCallbackHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.time.LocalDateTime
import kotlin.system.exitProcess

@SpringBootApplication
@ConfigurationPropertiesScan
class BatchTestApplication

fun main(args: Array<String>) {
    val exitCode = try {
        SpringApplication.exit(runApplication<BatchTestApplication>(*args))
    } catch (e: Exception) {
        5
    }

    exitProcess(exitCode)
}

@Component
class InitService(
    private val init: Init, private val readTableRepository: ReadTableRepository,
) {

    @PostConstruct
    fun init() {
//        init.init()
    }

    companion object {
        @Component
//        @Transactional
        class Init(
            val readTableRepository: ReadTableRepository,
            val jdbcTemplate: JdbcTemplate,
        ) {
            fun init() {
                for (i in 2_001..3_000) {
                    for (j in 0..9) {
                        jdbcTemplate.update(
                            "insert into write_table (so_id, ctrt_id, cust_id, pym_acnt_id, order_tp, created_date_time) values (?, ?, ?, ?, ?, ?)",
                            "01", "C" + "$i".padStart(9, '0'), "1" + "$i".padStart(14, '0'), "P" + "$i".padStart(9, '0'), "10$j", LocalDateTime.now()
                        )
//                        readTableRepository.save(ReadTable(IdGroup("01", "C" + "$i".padStart(9, '0'), "1" + "$i".padStart(14, '0'), "P" + "$i".padStart(9, '0'), "10$j")))
                    }
                }
            }
        }
    }
}