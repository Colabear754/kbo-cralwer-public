package com.colabear754.kbo_crawler.api.services

import com.colabear754.kbo_crawler.api.domain.entities.GameInfo
import com.colabear754.kbo_crawler.api.domain.enums.SeriesType
import com.colabear754.kbo_crawler.api.dto.responses.CollectDataResponse
import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

private fun Browser.scrapeGameInfo(season: Int, month: Int, series: SeriesType): List<GameInfo> {
    TODO("크롤링 로직 실제 구현 필요")
}

@Service
class GameScheduleCrawlingService(
    private val gameInfoDataService: GameInfoDataService
) {
    suspend fun collectAndSaveSeasonGameInfo(
        season: Int,
        seriesType: SeriesType?
    ): CollectDataResponse {
        // seriesType이 null이면 전체 시리즈 수집
        val seriesTypes = seriesType?.let { listOf(it) } ?: SeriesType.entries
        // 1월부터 12월까지 해당 시즌/시리즈의 경기 일정을 비동기 수집 후 취합
        val seasonGameInfo = launchChromium { coroutineScope {
            seriesTypes.flatMap { type ->
                (1..12).map { month -> async { scrapeGameInfo(season, month, type) } }
                    .awaitAll().flatten()
            }
        } }

        return gameInfoDataService.saveOrUpdateGameInfo(seasonGameInfo)
    }

    private suspend fun <R> launchChromium(action: suspend Browser.() -> R): R = Playwright.create().use { playwright ->
        playwright.chromium().launch(BrowserType.LaunchOptions().apply { headless = true }).use { browser ->
            browser.action()
        }
    }
}