package com.obatis.config.response.result;

import com.obatis.core.annotation.request.ReturnTypeValue;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 数据返回处理类，进行结果装载和数据判断
 * @author HuangLongPu
 */
public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {
	
    private final HandlerMethodReturnValueHandler delegate;

    public ResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        /**
         * 如果返回值已经是 ResultResponse 结构体或者方法体已经注册了 ReturnTypeValue 明确原样输出，则不进行任何结构化处理
          */
        if(returnValue instanceof ResultResponse || returnType.hasMethodAnnotation(ReturnTypeValue.class)) {
    		delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    	} else {
            ResultResponse resultInfo = new ResultResponse();
    		resultInfo.setResult(returnValue);
    		delegate.handleReturnValue(resultInfo, returnType, mavContainer, webRequest);
    	}
    }
}
