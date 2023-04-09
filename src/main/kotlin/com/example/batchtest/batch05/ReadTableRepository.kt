package com.example.batchtest.batch05

import org.springframework.data.jpa.repository.JpaRepository

interface ReadTableRepository : JpaRepository<ReadTable, IdGroup> {
}