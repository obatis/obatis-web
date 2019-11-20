package com.obatis.startup;

import com.obatis.config.response.result.callback.ExceptionRestHandle;
import com.obatis.config.response.result.callback.ExceptionRestHandleCallback;
import com.obatis.config.response.result.callback.ExceptionRestParam;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异常回调处理类
 */
public class HandleExceptionCallContext implements Runnable {

    protected static final ExecutorService exceptionHandlePool = Executors.newFixedThreadPool(2);

    private Map<String, ExceptionRestHandleCallback> beanMap;

    protected HandleExceptionCallContext(Map<String, ExceptionRestHandleCallback> beanMap) {
        this.beanMap = beanMap;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.handleException(ExceptionRestHandle.EXCEPTION_HANDLE_QUEUE.take());
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
