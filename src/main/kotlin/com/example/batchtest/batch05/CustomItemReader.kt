package com.example.batchtest.batch05

import org.springframework.batch.item.ItemReader

class CustomItemReader: ItemReader<ReadTableDto> {
    override fun read(): ReadTableDto? {
        TODO("Not yet implemented")
    }
}