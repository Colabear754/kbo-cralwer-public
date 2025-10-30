package com.colabear754.kbo_crawler.dto.global

import org.springframework.http.HttpStatus

data class GlobalResponse<T>(
    val code: HttpStatus,
    val message: String? = null,
    val data: T? = null
)