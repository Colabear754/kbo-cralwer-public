package com.colabear754.kbo_crawler.domain.enums

enum class SeriesType(
    val code: String,
    val seriesName: String
) {
    PRESEASON("1", "시범경기"),
    REGULAR_SEASON("0,9,6", "정규시즌"),
    POSTSEASON("3,4,5,7", "포스트시즌")
}