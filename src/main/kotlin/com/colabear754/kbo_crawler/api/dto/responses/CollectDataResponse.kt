package com.colabear754.kbo_crawler.api.dto.responses

data class CollectDataResponse(
    val collectedCount: Int,
    val savedCount: Int,
    val modifiedCount: Int
)
