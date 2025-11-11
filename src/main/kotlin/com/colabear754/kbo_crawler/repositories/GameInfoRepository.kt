package com.colabear754.kbo_crawler.repositories

import com.colabear754.kbo_crawler.domain.entities.GameInfo
import com.colabear754.kbo_crawler.domain.enums.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface GameInfoRepository : JpaRepository<GameInfo, Long> {
    fun findByGameKeyIn(gameKeys: List<String>): List<GameInfo>
    @Query("select g from GameInfo g where g.date = :date and (g.homeTeam = :team or g.awayTeam = :team)")
    fun findByDateAndTeam(date: LocalDate, team: Team): List<GameInfo>
    fun findByGameKey(gameKey: String): GameInfo
}