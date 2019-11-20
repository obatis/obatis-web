package com.obatis.config.response.result.callback;

public class ExceptionRestHandle {

    public static boolean ADD_EXCEPTION_FLAG = false;

    public static void addNullPointer(Exception exception) {
        HandleExceptionCallbackContext.addException(HandleTypeEnum.HANDLE_TYPE_NULL_POINTER, exception);
    }

    public static void addIndexOut(Exception exception) {
        HandleExceptionCallbackContext.addException(HandleTypeEnum.HANDLE_TYPE_INDEX_OUT, exception);
    }

    public static void addSql(Exception exception) {
        HandleExceptionCallbackContext.addException(HandleTypeEnum.HANDLE_TYPE_SQL, exception);
    }

    public static void addDefault(Exception exception) {
        HandleExceptionCallbackContext.addException(HandleTypeEnum.HANDLE_TYPE_DEFAULT, exception);
    }
}
