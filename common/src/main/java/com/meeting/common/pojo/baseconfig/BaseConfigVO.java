package com.meeting.common.pojo.baseconfig;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 基础配置
 * @dateTime 2019-07-19 12:42
 * @className com.meeting.meeting.pojo.baseconfig
 */
@ApiModel(value = "BaseConfigVO", description = "基础配置")
public class BaseConfigVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -5491222611283472306L;

    /** 基础配置主键ID */
    @ApiModelProperty(value = "基础配置主键ID")
    private Integer baseConfigId;
    /** 最多可拥有好友 */
    @ApiModelProperty(value = "最多可拥有好友")
    private int maxFriend;
    /** 最多可创建群组 */
    @ApiModelProperty(value = "最多可创建群组")
    private int maxGroup;
    /** 最多拥有群组成员 */
    @ApiModelProperty(value = "最多拥有群组成员")
    private int maxGroupMember;
    /** 秘钥 */
    @ApiModelProperty(value = "秘钥")
    private String secretKey;

    public int getMaxGroupMember() {
        return maxGroupMember;
    }

    public void setMaxGroupMember(int maxGroupMember) {
        this.maxGroupMember = maxGroupMember;
    }

    public Integer getBaseConfigId() {
        return baseConfigId;
    }

    public void setBaseConfigId(Integer baseConfigId) {
        this.baseConfigId = baseConfigId;
    }

    public int getMaxFriend() {
        return maxFriend;
    }

    public void setMaxFriend(int maxFriend) {
        this.maxFriend = maxFriend;
    }

    public int getMaxGroup() {
        return maxGroup;
    }

    public void setMaxGroup(int maxGroup) {
        this.maxGroup = maxGroup;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
