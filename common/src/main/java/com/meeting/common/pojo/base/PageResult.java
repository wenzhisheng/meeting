package com.meeting.common.pojo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dameizi
 * @description: 分页返回
 * @dateTime 2019-03-29 15:12
 * @className PageResult
 */
@ApiModel(value="分页返回",description="分页返回")
public class PageResult<T> {

    /** 当前页码，默认是第一页 */
    @ApiModelProperty(value = "当前页码，默认是第一页")
    private int pageNo = 1;
    /** 每页数量，默认是15 */
    @ApiModelProperty(value = "每页数量，默认是15")
    private int pageSize = 15;
    /** 总记录数 */
    @ApiModelProperty(value = "总记录数")
    private int totalRecord;
    /** 总页数 */
    @ApiModelProperty(value = "总页数")
    private int totalPage;
    /** 数据列表 */
    @ApiModelProperty(value = "数据列表")
    private List<?> result;

    public PageResult() {
        super();
    }

    public PageResult(int pageNo, int pageSize, int totalRecord, List<?> results) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.setResult(results);
        int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }

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

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
        int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
        this.setTotalPage(totalPage);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }
}
