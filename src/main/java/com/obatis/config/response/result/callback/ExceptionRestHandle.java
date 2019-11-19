package com.obatis.config.response.result.callback;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ExceptionRestHandle {

    public static final BlockingQueue<ExceptionRestParam> EXCEPTION_HANDLE_QUEUE = new LinkedBlockingQueue<>();

    public static void addNullPointer(Exception exception) {
        add(HandleTypeEnum.HANDLE_TYPE_NULL_POINTER, exception);
    }

    public static void addIndexOut(Exception exception) {
        add(HandleTypeEnum.HANDLE_TYPE_INDEX_OUT, exception);
    }

    public static void addSql(Exception exception) {
        add(HandleTypeEnum.HANDLE_TYPE_SQL, exception);
    }

    public static void addDefault(Exception exception) {
        add(HandleTypeEnum.HANDLE_TYPE_DEFAULT, exception);
    }

    private static void add(HandleTypeEnum handleType, Exception exception) {
//        ExceptionRestParam param = new ExceptionRestParam();
//        param.setHandleType(handleType);
//        param.setException(exception);
//        try {
//            EXCEPTION_HANDLE_QUEUE.put(param);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
