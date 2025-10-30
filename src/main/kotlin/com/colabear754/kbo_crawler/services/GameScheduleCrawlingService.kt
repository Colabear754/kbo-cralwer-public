package com.colabear754.kbo_crawler.services

import com.colabear754.kbo_crawler.domain.entities.GameInfo
import com.colabear754.kbo_crawler.domain.enums.SeriesType
import com.colabear754.kbo_crawler.dto.responses.CollectDataResponse
import com.colabear754.kbo_crawler.repositories.GameInfoRepository
import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

private fun Browser.scrapeGameInfo(season: Int, month: Int, series: SeriesType): List<GameInfo> {
    TODO("크롤링 로직 실제 구현 필요")
}

@Service
class GameScheduleCrawlingService(
    private val gameInfoRepository: GameInfoRepository,
    private val operator: TransactionalOperator
) {
    suspend fun collectAndSaveSeasonGameInfo(
        season: Int,
        series: SeriesType
    ): CollectDataResponse {
        // 1월부터 12월까지 해당 시즌/시리즈의 경기 일정을 비동기 수집 후 취합
        val seasonGameInfo = Playwright.create().use { playwright ->
            playwright.chromium().launch(BrowserType.LaunchOptions().apply { headless = true }).use { browser ->
                coroutineScope {
                    (1..12).map { month -> async { browser.scrapeGameInfo(season, month, series) } }
                        .awaitAll().flatten()
                }
            }
        }
        var modifiedCount = 0

        operator.executeAndAwait {
            val existingGamesMap = gameInfoRepository.findByGameKeyIn(seasonGameInfo.map { it.gameKey })
                .associateBy { it.gameKey }

            for (gameInfo in seasonGameInfo) {
                val existingGame = existingGamesMap[gameInfo.gameKey]
                if (existingGame == null) {
                    gameInfoRepository.save(gameInfo)
                    continue
                }
                existingGame.update(gameInfo)
                modifiedCount++
            }
        }

        return CollectDataResponse(seasonGameInfo.size, modifiedCount)
    }
}