package com.obatis.config.response.result;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
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
    	if(returnValue instanceof ResultResponse) {
    		delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    	} else {
            ResultResponse resultInfo = new ResultResponse();
    		resultInfo.setResult(returnValue);
    		delegate.handleReturnValue(resultInfo, returnType, mavContainer, webRequest);
    	}
    }
}
