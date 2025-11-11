package com.colabear754.kbo_crawler.controllers

import com.colabear754.kbo_crawler.domain.enums.Team
import com.colabear754.kbo_crawler.dto.requests.CollectDataRequest
import com.colabear754.kbo_crawler.dto.responses.CollectDataResponse
import com.colabear754.kbo_crawler.dto.responses.FindGameInfoResponse
import com.colabear754.kbo_crawler.services.GameInfoDataService
import com.colabear754.kbo_crawler.services.GameScheduleCrawlingService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/game-schedule")
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