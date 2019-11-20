package com.obatis.config.response.result.callback;

import com.obatis.config.SystemConstant;

import java.sql.SQLException;
import java.util.Map;

/**
 * 异常回调处理类
 */
public class HandleExceptionCallbackContext implements Runnable {

    private static Map<String, ExceptionRestHandleCallback> beanMap;

    public static void addException(HandleTypeEnum handleType, Exception exception) {
        if(!ExceptionRestHandle.ADD_EXCEPTION_FLAG) {
            return;
        }
        ExceptionRestParam param = new ExceptionRestParam();
        param.setHandleType(handleType);
        param.setException(exception);
        try {
            SystemConstant.HANDLE_QUEUE.put(param);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public HandleExceptionCallbackContext(Map<String, ExceptionRestHandleCallback> beanMap) {
        this.beanMap = beanMap;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.handleException(SystemConstant.HANDLE_QUEUE.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleException(ExceptionRestParam param) {
        for (Map.Entry<String, ExceptionRestHandleCallback> value : beanMap.entrySet()) {
            SystemConstant.HANDLE_POOL.execute(new HandleExceptionCallContextWorker(value.getValue(), param));
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
