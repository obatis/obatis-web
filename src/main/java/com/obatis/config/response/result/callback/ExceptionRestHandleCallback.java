package com.obatis.config.response.result.callback;

import com.obatis.email.exception.SendMailException;

import java.sql.SQLException;

/**
 * 异常回调接口，提供框架异常抛出回调，如果需要处理异常信息，需要定义一个spring bean 实现该接口
 */
public interface ExceptionRestHandleCallback {

    /**
     * 空指针异常
     * @param exception
     */
    void handleNullPointer(NullPointerException exception);

    /**
     * 数组越界异常
     * @param exception
     */
    void handleIndexOutOfBounds(IndexOutOfBoundsException exception);

    /**
     * SQL执行异常
     * @param exception
     */
    void handleSQL(SQLException exception);

    /**
     * 邮件发生异常
     * @param exception
     */
    void handleSendMail(SendMailException exception);

    /**
     * 程序执行异常，主要为不可见的 throw Exception
     * @param exception
     */
    void handle(Exception exception);
}
