package com.obatis.config.response.result;

import com.obatis.config.response.result.callback.ExceptionRestHandle;
import com.obatis.constant.http.ResponseErrorCode;
import com.obatis.constant.http.ResponseErrorStatus;
import com.obatis.core.exception.NotAuthHandleException;
import com.obatis.core.exception.NotLoginHandleException;
import com.obatis.core.logger.LogPrintFactory;
import com.obatis.core.logger.LogPrinter;
import com.obatis.email.exception.SendMailException;
import com.obatis.exception.HandleException;
import com.obatis.tools.ValidateTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
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
	public ResultResponse handler(HttpServletResponse response, Exception exception) {
		if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
			response.setStatus(HttpStatus.OK.value());
		}
		ResultResponse resultInfo = new ResultResponse();
		String errorCode;

		if (exception instanceof HandleException) {
			resultInfo.setCode(ResponseErrorStatus.BUS_ERROR_STATUS);
			resultInfo.setMessage(ValidateTool.isHaveChinese(exception.getMessage()) ? exception.getMessage() : "业务异常");
			errorCode = ((HandleException) exception).getErrCode();
			LOG.print(printExceptionLog(exception));
			ExceptionRestHandle.addDefault(exception);
		} else if (exception instanceof MethodArgumentNotValidException) {
			this.handleMethodArgumentBindBad(exception, ((MethodArgumentNotValidException) exception).getBindingResult(), resultInfo);
			errorCode = ResponseErrorCode.PARAM_INVALID_ERROR_CODE;
		} else if (exception instanceof BindException) {
			this.handleMethodArgumentBindBad(exception, ((BindException) exception).getBindingResult(), resultInfo);
			errorCode = ResponseErrorCode.PARAM_INVALID_ERROR_CODE;
		} else if (exception instanceof HttpMessageNotReadableException) {
			resultInfo.setCode(ResponseErrorStatus.PARAM_TYPE_ERROR_STATUS);
			resultInfo.setMessage("请求错误");
			errorCode = ResponseErrorCode.PARAM_TYPE_ERROR_CODE;
			LOG.print("请求参数值类型不匹配：" + exception.getMessage());
			ExceptionRestHandle.addDefault(exception);
		} else if (exception instanceof NoHandlerFoundException) {
			resultInfo.setCode(org.apache.http.HttpStatus.SC_NOT_FOUND);
			resultInfo.setMessage("HTTP请求URL地址不正确");
			errorCode = ResponseErrorCode.URL_NOT_FOUND_ERROR_CODE;
			LOG.print("HTTP请求URL地址不正确" + exception.getMessage());
			ExceptionRestHandle.addDefault(exception);
		} else if (exception instanceof HttpRequestMethodNotSupportedException || exception instanceof HttpMediaTypeNotSupportedException) {
			// HTTP请求类型不支持
			resultInfo.setCode(ResponseErrorStatus.METHOD_NOT_SUPPORT_ERROR_STATUS);
			resultInfo.setMessage("HTTP请求类型不支持");
			errorCode = ResponseErrorCode.METHOD_NOT_SUPPORT_ERROR_CODE;
			ExceptionRestHandle.addDefault(exception);
		} else if (exception instanceof NotAuthHandleException) {
			resultInfo.setCode(ResponseErrorStatus.NOT_AUTH_ERROR_STATUS);
			resultInfo.setMessage(ValidateTool.isHaveChinese(exception.getMessage()) ? exception.getMessage() : NotAuthHandleException.DEFAUTL_NOT_AUTH_MESSAGE);
			errorCode = ResponseErrorCode.NOT_AUTH_ERROR_CODE;
			LOG.print("请求未授权，没有操作权限" + (!ValidateTool.isEmpty(exception.getMessage()) ? "," + exception.getMessage() : ""));
			ExceptionRestHandle.addDefault(exception);
		} else if (exception instanceof NotLoginHandleException) {
			resultInfo.setCode(ResponseErrorStatus.NOT_LOGIN_ERROR_STATUS);
			resultInfo.setMessage(ValidateTool.isHaveChinese(exception.getMessage()) ? exception.getMessage() : NotLoginHandleException.DEFAULT_NOT_LOGIN_MESSAGE);
			errorCode = ResponseErrorCode.NOT_LOGIN_ERROR_CODE;
			LOG.print("用户未登录" + (!ValidateTool.isEmpty(exception.getMessage()) ? "," + exception.getMessage() : ""));
			ExceptionRestHandle.addDefault(exception);
		} else if (exception instanceof NullPointerException) {
			String trace = this.printExceptionLog(exception);
			resultInfo.setCode(ResponseErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("请求错误");
			errorCode = ResponseErrorCode.NULL_POINTER_ERROR_CODE;
			LOG.print("空指针异常：" + trace);
			ExceptionRestHandle.addNullPointer(exception);
		} else if (exception instanceof IndexOutOfBoundsException) {
			String trace = this.printExceptionLog(exception);
			resultInfo.setCode(ResponseErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("请求错误");
			errorCode = ResponseErrorCode.INDEX_OUT_ERROR_CODE;
			LOG.print("操作越界异常：" + trace);
			ExceptionRestHandle.addIndexOut(exception);
		} else if (exception instanceof SQLException) {
			String trace = this.printExceptionLog(exception);
			resultInfo.setCode(ResponseErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("请求错误");
			errorCode = ResponseErrorCode.SQL_EXECUTE_ERROR_CODE;
			LOG.print("SQL执行运行异常：" + trace);
			ExceptionRestHandle.addSql(exception);
		} else if (exception instanceof SendMailException) {
			String trace = this.printExceptionLog(exception);
			resultInfo.setCode(ResponseErrorStatus.SEND_MAIL_ERROR_STATUS);
			resultInfo.setMessage("请求错误");
			errorCode = ResponseErrorCode.SEND_MAIL_ERROR_CODE;
			LOG.print("邮件发送异常：" + trace);
			ExceptionRestHandle.addMail(exception);
		} else {
			String trace = this.printExceptionLog(exception);
			resultInfo.setCode(ResponseErrorStatus.SYSTEM_ERROR_STATUS);
			resultInfo.setMessage("请求错误");
			errorCode = ResponseErrorCode.SYSTEM_ERROR_CODE;
			LOG.print("程序执行错误：" + trace);
			ExceptionRestHandle.addDefault(exception);
		}

		if (ValidateTool.isEmpty(errorCode)) {
			errorCode = ResponseErrorCode.DEFAULT_ERROR_CODE;
		}
		resultInfo.setErrorCode(errorCode);
		return resultInfo;
	}

	private void handleMethodArgumentBindBad(Exception exception, BindingResult bindingResult, ResultResponse resultInfo) {
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		List<String> errorMsgs = new ArrayList<>();
		for (ObjectError errorInfo : allErrors) {
			FieldError fieldError = (FieldError) errorInfo;
			errorMsgs.add(fieldError.getDefaultMessage());
		}
		String join = String.join(",", errorMsgs);
		resultInfo.setCode(ResponseErrorStatus.PARAM_INVALID_ERROR_STATUS);
		resultInfo.setMessage(ValidateTool.isHaveChinese(join) ? join : "请求错误");
		LOG.print("请求参数值无效：" + join);
		ExceptionRestHandle.addDefault(exception);
	}

	/**
	 * 构建日志
	 * @param e
	 * @return
	 */
	private String printExceptionLog(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		e.printStackTrace(printWriter);
		stringWriter.flush();
		printWriter.flush();
        return stringWriter.getBuffer().toString();
	}
}
