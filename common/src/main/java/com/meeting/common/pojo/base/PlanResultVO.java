package com.meeting.common.pojo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: dameizi
 * @description: 数据输出
 * @dateTime 2019-03-29 15:36
 * @className PlanResultVO
 */
@ApiModel(value="数据输出",description="数据输出")
public class PlanResultVO implements Serializable {

    private static final long serialVersionUID = -3315188379467824933L;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "信息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private Object data;
    @ApiModelProperty(value = "编码")
    private Integer code;

    public PlanResultVO(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.code = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
