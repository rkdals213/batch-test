package com.example.batchtest.common

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

@Entity
data class ReadEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val readData: String,

    val dataType: Int
)

@Entity
class WriteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val readData: String,

    val dataType: Int,

    val registerName: String,

    val createdDateTime: LocalDateTime = LocalDateTime.now()
)

interface ReadRepository : JpaRepository<ReadEntity, Long> {
    fun findByIdGreaterThan(id: Long): List<ReadEntity>
}

interface WriteRepository : JpaRepository<WriteEntity, Long>