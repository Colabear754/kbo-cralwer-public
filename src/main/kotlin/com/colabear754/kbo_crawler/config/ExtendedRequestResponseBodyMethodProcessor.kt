package com.colabear754.kbo_crawler.config

import com.colabear754.kbo_crawler.dto.global.GlobalResponse
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

class ExtendedRequestResponseBodyMethodProcessor(
    converters: List<HttpMessageConverter<*>>
) : RequestResponseBodyMethodProcessor(converters) {
    override fun handleReturnValue(
        returnValue: Any?,
        returnType: MethodParameter,
        mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest
    ) {
        super.handleReturnValue(
            returnValue.let { if (it is String?) GlobalResponse(HttpStatus.OK, "성공", it) else it },
            returnType,
            mavContainer,
            webRequest
        )
    }
}