package com.colabear754.kbo_crawler.api.controllers

import com.colabear754.kbo_crawler.api.domain.enums.Team
import com.colabear754.kbo_crawler.api.dto.requests.CollectDataRequest
import com.colabear754.kbo_crawler.api.dto.responses.CollectDataResponse
import com.colabear754.kbo_crawler.api.dto.responses.FindGameInfoResponse
import com.colabear754.kbo_crawler.api.services.GameInfoDataService
import com.colabear754.kbo_crawler.api.services.GameScheduleCrawlingService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/game-schedule")
class GameScheduleController(
    private val gameScheduleCrawlingService: GameScheduleCrawlingService,
    private val gameInfoDataService: GameInfoDataService
) {
    @PostMapping("/collect")
    suspend fun collectGameScheduleData(@RequestBody request: CollectDataRequest): CollectDataResponse {
        return gameScheduleCrawlingService.collectAndSaveSeasonGameInfo(request.season, request.seriesType)
    }

    @GetMapping("/{team}/{date}")
    fun findGameInfo(@PathVariable team: Team, @PathVariable date: LocalDate): List<FindGameInfoResponse> {
        return gameInfoDataService.findGameInfoByTeamAndDate(date, team)
    }

    @GetMapping("/{gameKey}")
    fun findSpecificGameInfo(@PathVariable gameKey: String): FindGameInfoResponse? {
        return gameInfoDataService.findGameInfoByGameKey(gameKey)
    }
}