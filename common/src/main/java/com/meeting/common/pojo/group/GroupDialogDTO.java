package com.meeting.common.pojo.group;

import com.meeting.common.pojo.member.MemberDialogDTO;
import com.meeting.common.pojo.member.MemberVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author dameizi
 * @description 群组关系
 * @dateTime 2019-04-19 12:49
 * @className com.meeting.meeting.pojo.GroupVO
 */
@ApiModel(value = "GroupDialogDTO", description = "群组关系")
public class GroupDialogDTO implements Serializable {

    private static final long serialVersionUID = -2182602423581727712L;
    /** 群主键ID */
    @ApiModelProperty(value = "群主键ID")
    private Integer groupsId;
    /** 管理员ID */
    @ApiModelProperty(value = "管理员ID")
    private Integer memberId;
    /** 管理员账号 */
    @ApiModelProperty(value = "管理员账号")
    private String memberAccount;
    /** 群组账号 */
    @ApiModelProperty(value = "群组账号")
    private String account;
    /** 群组名称 */
    @ApiModelProperty(value = "群组名称")
    private String nickname;
    /** 群组人数 */
    @ApiModelProperty(value = "群组人数")
    private Integer groupCount;
    /** 群头像 */
    @ApiModelProperty(value = "群头像")
    private String avatar;
    /** 公告 */
    @ApiModelProperty(value = "公告")
    private String announcement;
    /** 聊天置顶（no：不置顶，yes：正常） */
    @ApiModelProperty(value = "聊天置顶（no：不置顶，yes：正常）")
    private String chatTop;
    /** 是否打扰（no：不打扰，yes：正常） */
    @ApiModelProperty(value = "是否打扰（no：不打扰，yes：正常）")
    private String isDisturb;
    /** 成员列表 */
    @ApiModelProperty(value = "成员列表")
    private List<MemberVO> listMember;

    public List<MemberVO> getListMember() {
        return listMember;
    }

    public void setListMember(List<MemberVO> listMember) {
        this.listMember = listMember;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
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

    public Integer getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(Integer groupsId) {
        this.groupsId = groupsId;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}
