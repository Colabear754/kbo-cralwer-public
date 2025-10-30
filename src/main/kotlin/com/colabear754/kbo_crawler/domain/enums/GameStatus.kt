package com.colabear754.kbo_crawler.domain.enums

enum class GameStatus(
    val statusName: String
) {
    SCHEDULED("예정"),
    CANCELED("취소"),
    PLAYING("경기 중"),
    FINISHED("경기 종료")
}