package com.meeting.common.pojo.navigation;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 主导航栏
 * @dateTime 2019-06-15 12:42
 * @className com.meeting.common.pojo.navigation.NavigationVO
 */
@ApiModel(value = "NavigationVO", description = "主导航栏")
public class NavigationVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -243516705165000985L;
    /** 主导航栏主键ID */
    @ApiModelProperty(value = "主导航栏主键ID")
    private Integer navigationId;
    /** 图标 */
    @ApiModelProperty(value = "图标")
    private String icon;
    /** 名称 */
    @ApiModelProperty(value = "名称")
    private String name;
    /** 排序 */
    @ApiModelProperty(value = "排序")
    private String sort;
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1：启用）")
    private Integer isEnable;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(Integer navigationId) {
        this.navigationId = navigationId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}
