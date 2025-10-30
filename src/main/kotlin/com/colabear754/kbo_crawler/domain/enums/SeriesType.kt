package com.colabear754.kbo_crawler.domain.enums

enum class SeriesType(
    val code: String
) {
    PRESEASON("1"),
    REGULAR_SEASON("0,9,6"),
    POSTSEASON("3,4,5,7")
}