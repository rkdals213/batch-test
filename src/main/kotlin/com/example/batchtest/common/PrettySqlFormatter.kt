package com.example.batchtest.common

import com.p6spy.engine.logging.Category
import com.p6spy.engine.logging.P6LogOptions
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration

@Configuration
class PrettySqlFormatter : MessageFormattingStrategy {

    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = this.javaClass.name
        val activeInstance = P6LogOptions.getActiveInstance()
        activeInstance.filter = true
        activeInstance.exclude = "BATCH_"
    }

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?
    ): String {
        return "[$category] | $elapsed ms | ${formatSql(category, sql)}"
    }

    private fun stackTrace(): String {
        return Throwable().stackTrace.filter { t ->
            t.toString().startsWith("batchtest") && !t.toString().contains(this.javaClass.name)
        }.toString()
    }

    private fun formatSql(category: String?, sql: String?): String? {
        return if (sql.isNullOrBlank()) {
            ""
        } else if (Category.STATEMENT.name.equals(category)) {
            val trim = sql.trim().lowercase()
            val query = if (trim.startsWith("create") || trim.startsWith("alter") || trim.startsWith("comment")) {
                FormatStyle.DDL.formatter.format(sql)
            } else {
                FormatStyle.BASIC.formatter.format(sql)
            }
            return """${stackTrace()}
                $query
            """.trimIndent()
        } else sql
    }
}