package com.obatis.config.response.result;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PageInfo<T> {

	@ApiModelProperty(value = "分页信息")
    private List<T> list;
    @ApiModelProperty(value = "总条数")
    private long total = 0;

    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
}
