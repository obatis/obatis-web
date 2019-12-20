package com.obatis.core.sign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignMethodConfigureHolder implements SignMethodConfigure {

    /**
     * 生成签名
     * @param accountToken
     * @param response
     * @return
     */
    @Override
    public String createResponseSign(String accountToken, HttpServletResponse response) {
        return null;
    }

    /**
     * 校验签名
     * @param request
     * @return
     */
    @Override
    public boolean validateRequestSign(String accountToken, HttpServletRequest request) {
        return false;
    }
}
