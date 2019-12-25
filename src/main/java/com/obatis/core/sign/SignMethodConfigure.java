package com.obatis.core.sign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口签名方法接口
 * 如果需要自定义签名方法，需要实现此接口，实现方法加上@Component或者 @Configurable，否则实现方法无效
 * 如果需要自定义加密方法，接口只需实现一次即可，无需多个类实现，若多个类实现，以取到的最后一个为准
 */
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
