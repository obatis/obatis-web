package com.obatis.core.sign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SignMethodConfigure {

    /**
     * 生成签名
     * @param response
     * @return
     */
    String createResponseSign(String accountToken, HttpServletResponse response);

    /**
     * 校验签名
     * @param request
     * @return
     */
    boolean validateRequestSign(String accountToken, HttpServletRequest request);
}
