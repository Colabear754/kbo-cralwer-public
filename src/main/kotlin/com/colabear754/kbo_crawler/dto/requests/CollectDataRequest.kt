package com.colabear754.kbo_crawler.dto.requests

import com.colabear754.kbo_crawler.domain.enums.SeriesType

data class CollectDataRequest(
    val season: Int,
    val series: SeriesType
)
