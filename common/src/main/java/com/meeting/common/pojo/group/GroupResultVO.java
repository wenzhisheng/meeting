package com.meeting.common.pojo.group;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 群组关系
 * @dateTime 2019-04-19 12:49
 * @className com.meeting.meeting.pojo.GroupVO
 */
@ApiModel(value = "GroupVO", description = "群组关系")
public class GroupResultVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -7288467000330719829L;

    /** 群主键ID */
    @ApiModelProperty(value = "群主键ID")
    private Integer groupId;
    /** 群会员主键ID */
    @ApiModelProperty(value = "群会员主键ID")
    private Integer groupMemberId;
    /** 管理员ID */
    @ApiModelProperty(value = "管理员ID")
    private Integer memberId;
    /** 管理员账号 */
    @ApiModelProperty(value = "管理员账号")
    private String memberAccount;
    /** 群组账号 */
    @ApiModelProperty(value = "群组账号")
    private String groupAccount;
    /** 群组名称 */
    @ApiModelProperty(value = "群组名称")
    private String groupName;
    /** 群组人数 */
    @ApiModelProperty(value = "群组人数")
    private Integer groupCount;
    /** 群头像 */
    @ApiModelProperty(value = "群头像")
    private String avatar;
    /** 简介 */
    @ApiModelProperty(value = "简介")
    private String intro;
    /** 公告 */
    @ApiModelProperty(value = "公告")
    private String notice;
    /** 聊天置顶（no：不置顶，yes：正常） */
    @ApiModelProperty(value = "聊天置顶（no：不置顶，yes：正常）")
    private String chatTop;
    /** 是否打扰（no：不打扰，yes：正常） */
    @ApiModelProperty(value = "是否打扰（no：不打扰，yes：正常）")
    private String isDisturb;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getChatTop() {
        return chatTop;
    }

    public void setChatTop(String chatTop) {
        this.chatTop = chatTop;
    }

    public String getIsDisturb() {
        return isDisturb;
    }

    public void setIsDisturb(String isDisturb) {
        this.isDisturb = isDisturb;
    }
}
