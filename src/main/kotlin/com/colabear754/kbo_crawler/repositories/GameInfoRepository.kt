package com.colabear754.kbo_crawler.repositories

import com.colabear754.kbo_crawler.domain.entities.GameInfo
import org.springframework.data.jpa.repository.JpaRepository

interface GameInfoRepository : JpaRepository<GameInfo, Long> {
    fun findByGameKeyIn(gameKeys: List<String>): List<GameInfo>
}