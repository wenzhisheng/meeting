package com.meeting.common.pojo.dialog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dameizi
 * @description 对话列表
 * @dateTime 2019-07-03 12:42
 * @className com.meeting.meeting.pojo.DialogVO
 */
@ApiModel(value = "DialogVO", description = "对话列表")
public class DialogVO implements Serializable {

    private static final long serialVersionUID = 6498167071003055L;
    /** 对话主键ID */
    @ApiModelProperty(value = "对话主键ID")
    private Integer dialogueId;
    /** 发送人ID */
    @ApiModelProperty(value = "发送人ID")
    private Integer memberId;
    /** 目标人ID（好友ID/群组ID） */
    @ApiModelProperty(value = "目标人ID（好友ID/群组ID）")
    private Integer targetId;
    /** 目标者昵称 */
    @ApiModelProperty(value = "目标者昵称")
    private String alias;
    /** 目标者头像 */
    @ApiModelProperty(value = "目标者头像")
    private String avatar;
    /** 目标者账号 */
    @ApiModelProperty(value = "目标者账号")
    private String account;
    /** 最后消息内容 */
    @ApiModelProperty(value = "最后消息内容")
    private String content;
    /** 最后消息标识 */
    @ApiModelProperty(value = "最后消息标识")
    private String msgid;
    /** 未读消息 */
    @ApiModelProperty(value = "未读消息数量")
    private int unread;
    /** 最后消息时间 ） */
    @ApiModelProperty(value = "最后消息时间")
    private Date gmtMessage;
    /** 对话类型(single:个人，group:群组，system:系统) */
    @ApiModelProperty(value = "对话类型(single:个人，group:群组，system:系统)")
    private String chatType;
    /** 群组人数 */
    @ApiModelProperty(value = "群组人数")
    private Integer groupCount;

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getDialogueId() {
        return dialogueId;
    }

    public void setDialogueId(Integer dialogueId) {
        this.dialogueId = dialogueId;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public Date getGmtMessage() {
        return gmtMessage;
    }

    public void setGmtMessage(Date gmtMessage) {
        this.gmtMessage = gmtMessage;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
}
