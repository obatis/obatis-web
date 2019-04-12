package com.sbatis.config.response;

import com.google.common.collect.Lists;
import com.sbatis.config.response.result.ResponseBodyWrapHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * 处理框架数据返回，进行结果封装
 * @author HuangLongPu
 */
public class ResponseResultHandleFactory {
	
	private ResponseResultHandleFactory() {}

    public static void handleResponseResultInfo(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = Lists.newArrayList(returnValueHandlers);
        handlers(handlerMethodReturnValueHandlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(handlerMethodReturnValueHandlers);
    }

    private static void handlers(List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers) {
        for (HandlerMethodReturnValueHandler handlerMethodReturnValueHandler : handlerMethodReturnValueHandlers) {
            if (handlerMethodReturnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                // 封装处理返回结果集
                ResponseBodyWrapHandler responseBodyWrapHandler = new ResponseBodyWrapHandler(handlerMethodReturnValueHandler);
                int index = handlerMethodReturnValueHandlers.indexOf(handlerMethodReturnValueHandler);
                handlerMethodReturnValueHandlers.set(index, responseBodyWrapHandler);
                break;
            }
        }
    }

}

