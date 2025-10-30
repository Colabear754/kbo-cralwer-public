package com.colabear754.kbo_crawler.controllers

import com.colabear754.kbo_crawler.dto.requests.CollectDataRequest
import com.colabear754.kbo_crawler.dto.responses.CollectDataResponse
import com.colabear754.kbo_crawler.services.GameScheduleCrawlingService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game-schedule")
class GameScheduleController(
    private val gameScheduleCrawlingService: GameScheduleCrawlingService
) {
    @PostMapping("/collect")
    suspend fun collectGameScheduleData(@RequestBody request: CollectDataRequest): CollectDataResponse {
        return gameScheduleCrawlingService.collectAndSaveSeasonGameInfo(request.season, request.series)
    }
}