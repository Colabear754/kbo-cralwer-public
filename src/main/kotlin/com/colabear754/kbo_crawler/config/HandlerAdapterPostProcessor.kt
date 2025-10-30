package com.colabear754.kbo_crawler.config

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

@Component
class HandlerAdapterPostProcessor : BeanPostProcessor {
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is RequestMappingHandlerAdapter) {
            val handlers = bean.returnValueHandlers ?: return bean
            val index = handlers.indexOfFirst { it is RequestResponseBodyMethodProcessor }

            bean.returnValueHandlers = buildList {
                addAll(handlers.take(index))
                add(ExtendedRequestResponseBodyMethodProcessor(bean.messageConverters))
                addAll(handlers.drop(index))
            }
        }
        return bean
    }
}