package com.obatis.config.response.result.callback;

public enum ExceptionHandleTypeEnum {

    /**
     * 空指针异常
     */
    HANDLE_TYPE_NULL_POINTER,
    /**
     * 数组(集合)越界
     */
    HANDLE_TYPE_INDEX_OUT,
    /**
     * sql执行异常
     */
    HANDLE_TYPE_SQL,
    /**
     * 邮件发送异常
     */
    HANDLE_TYPE_SEND_MAIL,
    /**
     * 默认
     */
    HANDLE_TYPE_DEFAULT
}
