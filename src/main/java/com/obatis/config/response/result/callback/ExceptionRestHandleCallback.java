package com.obatis.config.response.result.callback;

import java.sql.SQLException;

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
     * 程序执行异常，主要为不可见的 throw Exception
     * @param exception
     */
    void handle(Exception exception);
}
