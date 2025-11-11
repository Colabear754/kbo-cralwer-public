package com.colabear754.kbo_crawler.api.crawlers

import com.colabear754.kbo_crawler.api.domain.entities.GameInfo
import com.colabear754.kbo_crawler.api.domain.enums.CancellationReason
import com.colabear754.kbo_crawler.api.domain.enums.GameStatus
import com.colabear754.kbo_crawler.api.domain.enums.SeriesType
import com.colabear754.kbo_crawler.api.domain.enums.Team
import com.microsoft.playwright.Locator
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

internal fun parseGameSchedule(locators: List<Locator>, season: Int, seriesType: SeriesType): List<GameInfo> {
    val yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd")
    val gameCountMap = mutableMapOf<String, Int>()
    val gameInfoList = mutableListOf<GameInfo>()

    var currentDate = LocalDate.MIN  // 날짜 지정용 변수

    for (locator in locators) {
        val cells = locator.locator("td").all()
        // 데이터가 없으면 스킵
        if (cells.size == 1 || cells.first().innerText().contains("데이터가 없습니다.")) {
            continue
        }
        // 이동일은 스킵
        if (cells.last().innerText().trim() == "이동일") {
            continue
        }
        // 날짜가 있으면 날짜를 갱신하고 시작지점 = 1, 아니면 시작지점 = 0
        val startIndex = if (cells.first().getAttribute("class")?.contains("day") == true) {
            val (month, day) = cells.first().innerText().take(5).trim().split('.').map { it.toInt() }
            currentDate = LocalDate.of(season, month, day)
            1
        } else 0

        val timeText = cells[startIndex].innerText().replace("<b>", "").replace("</b>", "").trim()
        val time = LocalTime.parse(timeText)
        // 경기 정보 파싱
        val playRecord = cells[startIndex + 1].locator("span").allInnerTexts()
        val awayTeam = Team.findByTeamName(playRecord.first())
        val homeTeam = Team.findByTeamName(playRecord.last())
        var awayScore: Int? = null
        var homeScore: Int? = null
        if (playRecord.size > 3) {
            awayScore = playRecord[1].toInt()
            homeScore = playRecord[3].toInt()
        }

        val gameKey = "${currentDate.format(yyyyMMdd)}-$awayTeam-$homeTeam"
        val count = gameCountMap[gameKey] ?: 1
        gameCountMap[gameKey] = count + 1

        val cancellationReason = CancellationReason.fromString(cells[startIndex + 7].innerText().trim())
        val gameStatus = when {
            awayScore != null -> GameStatus.FINISHED
            cancellationReason != null -> GameStatus.CANCELED
            else -> GameStatus.SCHEDULED
        }

        val gameInfo = GameInfo(
            "$gameKey-$count",
            seriesType,
            currentDate,
            time,
            homeTeam,
            awayTeam,
            homeScore,
            awayScore,
            cells[startIndex + 6].innerText().trim(),
            cells[startIndex + 4].innerHTML().replace("<br>", ",").trim(),
            gameStatus,
            cancellationReason
        )

        gameInfoList.add(gameInfo)
    }

    return gameInfoList
}