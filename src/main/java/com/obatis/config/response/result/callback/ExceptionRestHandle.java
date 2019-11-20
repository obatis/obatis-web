package com.obatis.config.response.result.callback;

import com.obatis.startup.HandleExceptionCallContext;

public class ExceptionRestHandle {

    public static void addNullPointer(Exception exception) {
        HandleExceptionCallContext.addException(HandleTypeEnum.HANDLE_TYPE_NULL_POINTER, exception);
    }

    public static void addIndexOut(Exception exception) {
        HandleExceptionCallContext.addException(HandleTypeEnum.HANDLE_TYPE_INDEX_OUT, exception);
    }

    public static void addSql(Exception exception) {
        HandleExceptionCallContext.addException(HandleTypeEnum.HANDLE_TYPE_SQL, exception);
    }

    public static void addDefault(Exception exception) {
        HandleExceptionCallContext.addException(HandleTypeEnum.HANDLE_TYPE_DEFAULT, exception);
    }
}
