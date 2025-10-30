package com.colabear754.kbo_crawler.domain.entities

import com.colabear754.kbo_crawler.domain.enums.CancellationReason
import com.colabear754.kbo_crawler.domain.enums.GameStatus
import com.colabear754.kbo_crawler.domain.enums.Team
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class GameInfo(
    @Column(nullable = false, updatable = false, unique = true)
    val gameKey: String,
    @Column(nullable = false, updatable = false)
    val date: LocalDate,
    var time: LocalTime?,
    @Enumerated(EnumType.STRING)
    val homeTeam: Team,
    @Enumerated(EnumType.STRING)
    val awayTeam: Team,
    var homeScore: Int?,
    var awayScore: Int?,
    var stadium: String,
    var relay: String,
    var gameStatus: GameStatus,
    @Enumerated(EnumType.STRING)
    var cancellationReason: CancellationReason?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val gameId: Long? = null
    @CreatedDate
    lateinit var createdAt: LocalDateTime
        protected set
    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime
        protected set

    fun update(gameInfo: GameInfo) {
        this.time = gameInfo.time
        this.homeScore = gameInfo.homeScore
        this.awayScore = gameInfo.awayScore
        this.stadium = gameInfo.stadium
        this.relay = gameInfo.relay
        this.gameStatus = gameInfo.gameStatus
        this.cancellationReason = gameInfo.cancellationReason
    }
}