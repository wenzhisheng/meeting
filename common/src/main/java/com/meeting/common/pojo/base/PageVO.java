package com.meeting.common.pojo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: dameizi
 * @description: 分页参数
 * @dateTime 2019-03-29 15:13
 * @className PageVO
 */
@ApiModel(value="分页参数",description="分页参数")
public class PageVO implements Serializable {

    private static final long serialVersionUID = 4597311999668675438L;
    /** 页码 */
    @ApiModelProperty(value="页码")
    private int pageNo = 1;
    /** 每页条数 */
    @ApiModelProperty(value="每页条数")
    private int pageSize = 10;
    /** 开始索引 */
    @ApiModelProperty(value="开始索引")
    private int startIndex;
    /** 结束索引 */
    @ApiModelProperty(value="结束索引")
    private int endIndex;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return (this.pageNo -1)*pageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex = this.pageSize;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
