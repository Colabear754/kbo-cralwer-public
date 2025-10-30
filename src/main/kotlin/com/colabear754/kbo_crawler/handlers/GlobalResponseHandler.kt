package com.colabear754.kbo_crawler.handlers

import com.colabear754.kbo_crawler.dto.global.GlobalResponse
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class GlobalResponseHandler : ResponseBodyAdvice<Any?> {
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>?>
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>?>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        return if (body?.javaClass != GlobalResponse::class.java) {
            val servletResponse = (response as? ServletServerHttpResponse)?.servletResponse
            GlobalResponse(HttpStatus.valueOf(servletResponse?.status ?: 500), "성공", body)
        } else body
    }
}