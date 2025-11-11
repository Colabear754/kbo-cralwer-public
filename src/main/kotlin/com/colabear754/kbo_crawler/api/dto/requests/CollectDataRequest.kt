package com.colabear754.kbo_crawler.api.dto.requests

import com.colabear754.kbo_crawler.api.domain.enums.SeriesType

data class CollectDataRequest(
    val season: Int,
    val seriesType: SeriesType?
)
