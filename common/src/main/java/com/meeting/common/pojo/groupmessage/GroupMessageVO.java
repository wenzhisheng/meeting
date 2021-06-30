package com.meeting.common.pojo.groupmessage;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 群消息
 * @dateTime 2019-04-19 13:08
 * @className com.meeting.meeting.pojo.GroupMessageVO
 */
@ApiModel(value = "GroupMessageVO", description = "群消息")
public class GroupMessageVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 5088860654428598150L;

    /** 群消息主键ID */
    @ApiModelProperty(value = "群消息主键ID")
    private Integer groupMessageId;
    /** 发送人ID */
    @ApiModelProperty(value = "发送人ID")
    private Integer sendId;
    /** 发送人账号 */
    @ApiModelProperty(value = "发送人账号")
    private String sendAccount;
    /** 群组ID */
    @ApiModelProperty(value = "群组ID")
    private Integer groupId;
    /** 群组账号 */
    @ApiModelProperty(value = "群组账号")
    private String groupAccount;
    /** 消息内容 */
    @ApiModelProperty(value = "消息内容")
    private String content;
    /** 消息标识 */
    @ApiModelProperty(value = "消息标识")
    private String msgid;
    /** 是否已读（0：否 1：是） */
    @ApiModelProperty(value = "是否已读（0：否 1：是）")
    private Integer isRead;
    /** 是否删除（0：否 1：是） */
    @ApiModelProperty(value = "是否删除（0：否 1：是）")
    private Integer isDel;
    /** 是否撤回（0：否 1：是） */
    @ApiModelProperty(value = "是否撤回（0：否 1：是）")
    private Integer isBack;
    /** 消息类型（text：文字，file：文件，voice：语音，video：视频） */
    @ApiModelProperty(value = "消息类型（text：文字，file：文件，voice：语音，video：视频）")
    private String messageType;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupMessageId() {
        return groupMessageId;
    }

    public void setGroupMessageId(Integer groupMessageId) {
        this.groupMessageId = groupMessageId;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public String getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(String sendAccount) {
        this.sendAccount = sendAccount;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }
}
