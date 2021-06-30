package com.meeting.common.pojo.base;

import com.meeting.common.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @dateTime 2019-03-26 23:49
 * @author: dameizi
 * @description: 基础信息
 */
@ApiModel(value="基础信息",description="基础信息")
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 6453314448843993576L;

    /** 创建时间 */
    @ApiModelProperty(value="创建时间", hidden=true)
    private Date gmtCreate;
    /** 修改时间 */
    @ApiModelProperty(value="修改时间", hidden=true)
    private Date gmtModified;
    /** 开始时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" ,iso=DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(value="开始日期 yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /** 结束时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss",iso=DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(value="结束日期 yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /** 批量操作数组 */
    @ApiModelProperty(value = "批量操作数组", hidden=true)
    private Integer[] ids;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
