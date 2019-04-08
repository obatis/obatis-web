package com.sbatis.config.response.result;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PageResultInfo<T> {

	@ApiModelProperty(value = "分页信息")
    private List<T> info;
    @ApiModelProperty(value = "总条数")
    private long total = 0;

    public List<T> getInfo() {
        return info;
    }
    public void setInfo(List<T> info) {
        this.info = info;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
}
