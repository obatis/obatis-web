package com.sbatis.config.response.result;

import com.sbatis.constant.http.ResponseDefaultErrorCode;
import com.sbatis.constant.http.ResponseDefaultErrorStatus;

/**
 * 微服务请求熔断回调结果集处理
 * @author HuangLongPu
 */
public class FallBackResultInfo {

	protected FallBackResultInfo(){}
	
	public static final ResultInfo RESULT = new ResultInfo();
	
	static {
		RESULT.setStatus(ResponseDefaultErrorStatus.FALL_BACK_ERROR_STATUS);
		RESULT.setMessage("请求错误，检查网络情况");
		RESULT.setErrorCode(ResponseDefaultErrorCode.NETWORK_ERROR_CODE);
	}

}
