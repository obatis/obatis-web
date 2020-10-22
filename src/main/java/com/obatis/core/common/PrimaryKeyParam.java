package com.obatis.core.common;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *  id 查询参数实体
 */
public class PrimaryKeyParam implements Serializable {

    @ApiModelProperty("主键ID")
    @NotNull(message = "ID不能为空")
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
