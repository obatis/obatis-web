package com.obatis.startup;

import com.obatis.config.response.result.callback.ExceptionRestHandleCallback;
import com.obatis.config.response.result.callback.ExceptionRestParam;
import com.obatis.config.response.result.callback.HandleTypeEnum;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 异常回调处理类
 */
public class HandleExceptionCallContext implements Runnable {

    protected static final ExecutorService exceptionHandlePool = Executors.newFixedThreadPool(2);

    public static final BlockingQueue<ExceptionRestParam> EXCEPTION_HANDLE_QUEUE = new LinkedBlockingQueue<>();

    private static Map<String, ExceptionRestHandleCallback> beanMap;

    public static void addException(HandleTypeEnum handleType, Exception exception) {
        if(beanMap == null || !beanMap.isEmpty()) {
            return;
        }
        ExceptionRestParam param = new ExceptionRestParam();
        param.setHandleType(handleType);
        param.setException(exception);
        try {
            EXCEPTION_HANDLE_QUEUE.put(param);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected HandleExceptionCallContext(Map<String, ExceptionRestHandleCallback> beanMap) {
        this.beanMap = beanMap;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.handleException(EXCEPTION_HANDLE_QUEUE.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleException(ExceptionRestParam param) {
        for (Map.Entry<String, ExceptionRestHandleCallback> value : beanMap.entrySet()) {
            exceptionHandlePool.execute(new HandleExceptionCallContextWorker(value.getValue(), param));
        }
    }

    class HandleExceptionCallContextWorker implements Runnable {

        private ExceptionRestHandleCallback exceptionRestHandleCallback;
        private ExceptionRestParam param;

        protected HandleExceptionCallContextWorker(ExceptionRestHandleCallback exceptionRestHandleCallback, ExceptionRestParam param) {
            this.exceptionRestHandleCallback = exceptionRestHandleCallback;
            this.param = param;
        }

        @Override
        public void run() {
            switch (param.getHandleType()) {
                case HANDLE_TYPE_NULL_POINTER:
                    exceptionRestHandleCallback.handleNullPointer((NullPointerException) param.getException());
                    break;
                case HANDLE_TYPE_INDEX_OUT:
                    exceptionRestHandleCallback.handleIndexOutOfBounds((IndexOutOfBoundsException) param.getException());
                    break;
                case HANDLE_TYPE_SQL:
                    exceptionRestHandleCallback.handleSQL((SQLException) param.getException());
                    break;
                case HANDLE_TYPE_DEFAULT:
                    exceptionRestHandleCallback.handle(param.getException());
                    break;
            }
        }
    }
}
