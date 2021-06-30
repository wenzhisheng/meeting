package com.meeting.common.pojo.friendapply;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 好友申请
 * @dateTime 2019-04-24 21:53
 * @className com.meeting.meeting.pojo.FriendApplyVO
 */
@ApiModel(value = "FriendApplyVO", description = "好友申请")
public class FriendApplyVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -304760009936329240L;

    /** 好友申请主键ID */
    @ApiModelProperty(value = "好友申请主键ID")
    private Integer friendApplyId;
    /** 发送人ID */
    @ApiModelProperty(value = "发送人ID")
    private Integer memberId;
    /** 发送人ID */
    @ApiModelProperty(value = "目标人ID")
    private Integer targetId;
    /** 申请状态（0：等待中 1：已同意 2：已过期） */
    @ApiModelProperty(value = "申请状态（0：等待中 1：已同意 2：已过期）")
    private Integer status;
    /** 备注信息 */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    public Integer getFriendApplyId() {
        return friendApplyId;
    }

    public void setFriendApplyId(Integer friendApplyId) {
        this.friendApplyId = friendApplyId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
