package com.colabear754.kbo_crawler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KboCrawlerApplication

fun main(args: Array<String>) {
    runApplication<KboCrawlerApplication>(*args)
}
