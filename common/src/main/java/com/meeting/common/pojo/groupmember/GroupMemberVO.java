package com.meeting.common.pojo.groupmember;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 群组会员
 * @dateTime 2019-04-21 13:24
 * @className com.meeting.meeting.pojo.GroupMemberVO
 */
@ApiModel(value = "GroupMemberVO", description = "群组会员")
public class GroupMemberVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 3184427764540565520L;

    /** 群会员主键ID */
    @ApiModelProperty(value = "群会员主键ID")
    private Integer groupMemberId;
    /** 会员ID */
    @ApiModelProperty(value = "会员ID")
    private Integer memberId;
    /** 会员账号 */
    @ApiModelProperty(value = "会员账号")
    private String memberAccount;
    /** 群组ID */
    @ApiModelProperty(value = "群组ID")
    private Integer groupId;

    /** 会员ID数组，添加群组成员 */
    @ApiModelProperty(value = "会员ID数组")
    private Integer[] memberIds;

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public Integer[] getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Integer[] memberIds) {
        this.memberIds = memberIds;
    }

    public Integer getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(Integer groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
