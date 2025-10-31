package com.colabear754.kbo_crawler.handlers

import com.colabear754.kbo_crawler.dto.global.GlobalResponse
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<GlobalResponse<*>> {
        val message = when (val cause = e.cause) {
            is InvalidFormatException -> {
                val fieldName = cause.path?.lastOrNull()?.fieldName ?: "Unknown Field"
                if (cause.targetType?.isEnum == true) "'$fieldName'에 유효하지 않은 값이 입력되었습니다."
                else "'$fieldName'에 유효하지 않은 형식의 값이 입력되었습니다."
            }
            else -> "요청 형식이 올바르지 않습니다."
        }
        return ResponseEntity.badRequest().body(GlobalResponse.error(HttpStatus.BAD_REQUEST, message))
    }

    @ExceptionHandler(NotImplementedError::class)
    fun handleNotImplementedError(e: NotImplementedError): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError()
            .body(ErrorResponse.create(e, HttpStatusCode.valueOf(501), e.message ?: "Not Implemented."))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(): ResponseEntity<GlobalResponse<*>> {
        return ResponseEntity.internalServerError()
            .body(GlobalResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."))
    }
}