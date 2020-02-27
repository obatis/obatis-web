package com.obatis.config.response.result;

import com.obatis.constant.http.ResponseDefaultErrorCode;
import com.obatis.convert.JsonCommonConvert;
import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RestRequestNoHandlerFoundException implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    public Object error(HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.SC_OK);

        ResultResponse resultInfo = new ResultResponse();
        resultInfo.setCode(org.apache.http.HttpStatus.SC_NOT_FOUND);
        resultInfo.setMessage("HTTP请求URL地址不正确");
        resultInfo.setErrorCode(ResponseDefaultErrorCode.URL_NOT_FOUND_ERROR_CODE);
        try {
            response.getWriter().write(JsonCommonConvert.objConvertJson(resultInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
