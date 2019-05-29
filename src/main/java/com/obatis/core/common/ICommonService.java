package com.obatis.core.common;

import com.obatis.core.exception.HandleException;

import java.math.BigInteger;

public interface ICommonService<T> {

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
