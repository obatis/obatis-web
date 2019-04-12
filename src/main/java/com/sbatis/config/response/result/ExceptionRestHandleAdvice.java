package com.sbatis.config.response.result;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sbatis.config.message.HttpMessageBigIntConvertSerializer;
import com.sbatis.constant.NormalCommonConstant;
import com.sbatis.constant.http.ResponseDefaultErrorCode;
import com.sbatis.constant.http.ResponseDefaultErrorStatus;
import com.sbatis.core.exception.HandleException;
import com.sbatis.core.exception.NotAuthHandleException;
import com.sbatis.core.exception.NotLoginHandleException;
import com.sbatis.core.logger.LogPrintFactory;
import com.sbatis.core.logger.LogPrinter;
import com.sbatis.validate.ValidateTool;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 异常统一拦截处理，封装提示信息等
 * @author HuangLongPu
 */
@RestControllerAdvice
public class ExceptionRestHandleAdvice {

	private final static LogPrinter LOG = LogPrintFactory.getLogPrint(ExceptionRestHandleAdvice.class);

	@ExceptionHandler(value = Exception.class)
	public ResultHandle handler(HttpServletResponse response, Exception exception) {
		if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
			response.setStatus(HttpStatus.OK.value());
		}
		ResultHandle resultInfo = new ResultHandle();
		String errorCode = null;

		if (exception instanceof HandleException) {
			LOG.print(exception.getMessage());
			resultInfo.setStatus(ResponseDefaultErrorStatus.BUS_ERROR_STATUS);
			resultInfo.setMessage(ValidateTool.isHaveChinese(exception.getMessage()) ? exception.getMessage() : "业务异常");
			errorCode = ((HandleException) exception).getErrCode();
		} else if (exception instanceof MethodArgumentNotValidException) {
			System.out.println(exception.getMessage());
			MethodArgumentNotValidException e1 = (MethodArgumentNotValidException) exception;
			BindingResult bindingResult = e1.getBindingResult();
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			List<String> errorMsgs = new ArrayList<>();
			for (ObjectError errorInfo : allErrors) {
				FieldError fieldError = (FieldError) errorInfo;
				errorMsgs.add(fieldError.getDefaultMessage());
			}
			String join = String.join(",", errorMsgs);
			resultInfo.setStatus(ResponseDefaultErrorStatus.PARAM_INVALID_ERROR_STATUS);
			resultInfo.setMessage(ValidateTool.isHaveChinese(join) ? join : "请求参数值无效");
			errorCode = ResponseDefaultErrorCode.PARAM_INVALID_ERROR_CODE;
			LOG.print("请求参数值无效：" + join);
		} else if (exception instanceof HttpMessageNotReadableException) {
			resultInfo.setStatus(ResponseDefaultErrorStatus.PARAM_TYPE_ERROR_STATUS);
			resultInfo.setMessage("请求参数值类型不匹配");
			errorCode = ResponseDefaultErrorCode.PARAM_TYPE_ERROR_CODE;
			LOG.print("请求参数值类型不匹配：" + exception.getMessage());
		} else if (exception instanceof NotAuthHandleException) {
			resultInfo.setStatus(ResponseDefaultErrorStatus.NOT_AUTH_ERROR_STATUS);
			resultInfo.setMessage("请求未授权，没有操作权限");
			errorCode = ResponseDefaultErrorCode.NOT_AUTH_ERROR_CODE;
			LOG.print("请求未授权，没有操作权限");
		} else if (exception instanceof NotLoginHandleException) {
			resultInfo.setStatus(ResponseDefaultErrorStatus.NOT_LOGIN_ERROR_STATUS);
			resultInfo.setMessage("用户未登录");
			errorCode = ResponseDefaultErrorCode.NOT_LOGIN_ERROR_CODE;
			LOG.print("用户未登录," + exception.getMessage());
		} else if (exception instanceof NoHandlerFoundException) {
			resultInfo.setStatus(org.apache.http.HttpStatus.SC_NOT_FOUND);
			resultInfo.setMessage("HTTP请求URL地址不正确");
			errorCode = ResponseDefaultErrorCode.URL_NOT_FOUND_ERROR_CODE;
			LOG.print("HTTP请求URL地址不正确" + exception.getMessage());
		} else if (exception instanceof NullPointerException) {
			String trace = printStackTrace(exception);
			resultInfo.setStatus(ResponseDefaultErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("执行错误");
			errorCode = ResponseDefaultErrorCode.NULL_POINTER_ERROR_CODE;
			LOG.print("空指针异常：" + trace);
		} else if (exception instanceof IndexOutOfBoundsException) {
			String trace = printStackTrace(exception);
			resultInfo.setStatus(ResponseDefaultErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("业务运行错误");
			errorCode = ResponseDefaultErrorCode.INDEX_OUT_ERROR_CODE;
			LOG.print("操作越界异常：" + trace);
		} else if (exception instanceof SQLException) {
			String trace = printStackTrace(exception);
			resultInfo.setStatus(ResponseDefaultErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("执行错误");
			errorCode = ResponseDefaultErrorCode.SQL_EXECUTE_ERROR_CODE;
			LOG.print("SQL执行运行异常：" + trace);
		} else {
			String trace = printStackTrace(exception);
			resultInfo.setStatus(ResponseDefaultErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("执行错误");
			errorCode = ResponseDefaultErrorCode.SYSTEM_ERROR_CODE;
			LOG.print("程序执行错误：" + trace);
		}

		if (ValidateTool.isEmpty(errorCode)) {
			errorCode = ResponseDefaultErrorCode.DEFAULT_ERROR_CODE;
		}
		resultInfo.setErrorCode(errorCode);
		return resultInfo;
	}

	private static String printStackTrace(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		e.printStackTrace(printWriter);
		stringWriter.flush();
		printWriter.flush();
        return stringWriter.getBuffer().toString();
	}
}
