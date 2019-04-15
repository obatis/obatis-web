package com.obatis.config.response.result;

import com.obatis.constant.http.ResponseDefaultErrorCode;
import com.obatis.constant.http.ResponseDefaultErrorStatus;

/**
 * 微服务请求熔断回调结果集处理
 * @author HuangLongPu
 */
public class FailBackResultHandle {

	protected FailBackResultHandle(){}
	
	public static final ResultHandle RESULT = new ResultHandle();
	
	static {
		RESULT.setStatus(ResponseDefaultErrorStatus.FAIL_BACK_ERROR_STATUS);
		RESULT.setMessage("请求错误，检查网络情况");
		RESULT.setErrorCode(ResponseDefaultErrorCode.NETWORK_ERROR_CODE);
	}

}
