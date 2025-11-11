package com.colabear754.kbo_crawler.api.domain.entities

import com.colabear754.kbo_crawler.api.domain.enums.CancellationReason
import com.colabear754.kbo_crawler.api.domain.enums.GameStatus
import com.colabear754.kbo_crawler.api.domain.enums.SeriesType
import com.colabear754.kbo_crawler.api.domain.enums.Team
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class GameInfo(
    @Size(max = 25)
    @Column(unique = true, nullable = false, updatable = false, length = 25)
    val gameKey: String,
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    val seriesType: SeriesType,
    @Column(nullable = false, updatable = false)
    val date: LocalDate,
    var time: LocalTime?,
    @Enumerated(EnumType.STRING)
    val homeTeam: Team,
    @Enumerated(EnumType.STRING)
    val awayTeam: Team,
    var homeScore: Int?,
    var awayScore: Int?,
    @Size(max = 20)
    @Column(length = 20)
    var stadium: String,
    @Size(max = 100)
    @Column(length = 100)
    var relay: String?,
    @Enumerated(EnumType.STRING)
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

    fun update(gameInfo: GameInfo): Boolean {
        val isUpdated = this.time?.equals(gameInfo.time) == false
                || this.homeScore != gameInfo.homeScore
                || this.awayScore != gameInfo.awayScore
                || this.stadium != gameInfo.stadium
                || this.relay != gameInfo.relay
                || this.gameStatus != gameInfo.gameStatus
                || this.cancellationReason != gameInfo.cancellationReason

        this.time = gameInfo.time
        this.homeScore = gameInfo.homeScore
        this.awayScore = gameInfo.awayScore
        this.stadium = gameInfo.stadium
        this.relay = gameInfo.relay
        this.gameStatus = gameInfo.gameStatus
        this.cancellationReason = gameInfo.cancellationReason

        return isUpdated
    }
}