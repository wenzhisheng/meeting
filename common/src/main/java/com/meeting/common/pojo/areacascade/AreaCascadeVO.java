package com.meeting.common.pojo.areacascade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author dameizi
 * @description 地区级联
 * @dateTime 2019-07-11 12:18
 * @className com.meeting.common.pojo.areacascade.AreaCascadeVO
 */
@ApiModel(value = "AreaCascadeVO", description = "地区级联")
public class AreaCascadeVO implements Serializable {

    private static final long serialVersionUID = -4402904701791983523L;

    /** 地区主键ID */
    @ApiModelProperty(value = "地区主键ID")
    private Integer areaId;
    /** 地区编码 */
    @ApiModelProperty(value = "地区编码")
    private String areaCode;
    /** 地区名 */
    @ApiModelProperty(value = "地区名")
    private String areaName;
    /** 地区级别（1:省份province,2:市city,3:区县district,4:街道street） */
    @ApiModelProperty(value = "地区级别（1:省份province,2:市city,3:区县district,4:街道street）")
    private Integer level;
    /** 城市编码 */
    @ApiModelProperty(value = "城市编码")
    private String cityCode;
    /** 城市中心点（即：经纬度坐标） */
    @ApiModelProperty(value = "城市中心点（即：经纬度坐标）")
    private String center;
    /** 地区父节点 */
    @ApiModelProperty(value = "地区父节点")
    private Integer parentId;

    /** 子区域列表 */
    private List<AreaCascadeVO> districts;

    public List<AreaCascadeVO> getDistricts() {
        return districts;
    }

    public void setDistricts(List<AreaCascadeVO> districts) {
        this.districts = districts;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
