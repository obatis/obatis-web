package com.sbatis.config.response.result;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class PageResultInfo<T> {

	@ApiModelProperty(value = "分页信息")
    private List<T> rows;
    @ApiModelProperty(value = "总数")
    private long total = 0;
    @ApiModelProperty(value = "当前页")
    private long pageNo;
    @ApiModelProperty(value = "当前页总数")
    private long pageSize;
    @ApiModelProperty(value = "总页数")
    private long pages;
    

}
