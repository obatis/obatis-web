package com.obatis.config.response.result;

import com.obatis.constant.http.ResponseErrorCode;
import com.obatis.constant.http.ResponseErrorStatus;

/**
 * 微服务请求熔断回调结果集处理
 * @author HuangLongPu
 */
public class FailBackResponse {

	protected FailBackResponse(){}
	
	public static final ResultResponse RESULT = new ResultResponse();
	
	static {
		RESULT.setCode(ResponseErrorStatus.FAIL_BACK_ERROR_STATUS);
		RESULT.setMessage("请求错误，检查网络情况");
		RESULT.setErrorCode(ResponseErrorCode.NETWORK_ERROR_CODE);
	}

}
