package com.obatis.core.common;

import com.obatis.exception.HandleException;

import java.math.BigInteger;

/**
 * 公共接口基类，开发者可根据实际需要进行继承
 * @param <T>
 */
public interface ICommonService<T> {

    /**
     * 添加数据
     * @param t
     * @return
     * @throws HandleException
     */
    int insert(T t) throws HandleException;

    /**
     * 根据ID删除数据
     * @param id
     * @return
     * @throws HandleException
     */
    int deleteById(BigInteger id) throws HandleException;

    /**
     * 根据ID查询实体数据
     * @param id
     * @return
     */
    T findById(BigInteger id);
}
