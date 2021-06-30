package com.meeting.common.pojo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: dameizi
 * @description: 批量更新
 * @dateTime 2019-03-29 15:16
 * @className BatchUpdateVO
 */
@ApiModel(value="批量更新",description="批量更新")
public class BatchUpdateVO implements Serializable {

    private static final long serialVersionUID = 3157300613668842692L;
    /** 主键 */
    @ApiModelProperty(value="更新主键",required=true)
    private String ids;
    /** 是否启用 0未启用 1已启用 */
    @ApiModelProperty(value="是否启用（0：禁用 1：启用）",required=true)
    private String isEnable;

    @Override
    public String toString() {
        return "BatchUpdateVO{" +
                "ids='" + ids + '\'' +
                ", isEnable='" + isEnable + '\'' +
                '}';
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
}
