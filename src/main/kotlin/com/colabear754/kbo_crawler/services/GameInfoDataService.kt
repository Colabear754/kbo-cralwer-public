package com.colabear754.kbo_crawler.services

import com.colabear754.kbo_crawler.domain.entities.GameInfo
import com.colabear754.kbo_crawler.dto.responses.CollectDataResponse
import com.colabear754.kbo_crawler.repositories.GameInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GameInfoDataService(
    private val gameInfoRepository: GameInfoRepository
) {
    @Transactional
    fun saveOrUpdateGameInfo(seasonGameInfo: List<GameInfo>): CollectDataResponse {
        var savedCount = 0
        var modifiedCount = 0

        val existingGamesMap = gameInfoRepository.findByGameKeyIn(seasonGameInfo.map { it.gameKey })
            .associateBy { it.gameKey }

        for (gameInfo in seasonGameInfo) {
            val existingGame = existingGamesMap[gameInfo.gameKey]
            if (existingGame == null) {
                gameInfoRepository.save(gameInfo)
                savedCount++
                continue
            }
            val isUpdated = existingGame.update(gameInfo)
            if (isUpdated) {
                modifiedCount++
            }
        }

        return CollectDataResponse(seasonGameInfo.size, savedCount, modifiedCount)
    }
}