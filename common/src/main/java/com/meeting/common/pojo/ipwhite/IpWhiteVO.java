package com.meeting.common.pojo.ipwhite;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 群组关系
 * @dateTime 2019-07-19 12:49
 * @className com.meeting.meeting.pojo.ipwhite
 */
@ApiModel(value = "IpWhiteVO", description = "IP白名单")
public class IpWhiteVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1361527495619669904L;

    /** IP白名单主键ID */
    @ApiModelProperty(value = "IP白名单主键ID")
    private Integer id;
    /** IP */
    @ApiModelProperty(value = "IP")
    private String ip;
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1：启用）")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
