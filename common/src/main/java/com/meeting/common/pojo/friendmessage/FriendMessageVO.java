package com.meeting.common.pojo.friendmessage;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 好友消息
 * @dateTime 2019-04-19 13:03
 * @className com.meeting.meeting.pojo.FriendMessageVO
 */
@ApiModel(value = "FriendMessageVO", description = "好友消息")
public class FriendMessageVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -8907622031184235575L;

    /** 好友消息主键ID */
    @ApiModelProperty(value = "好友消息主键ID")
    private Integer friendMessageId;
    /** 发送人ID */
    @ApiModelProperty(value = "发送人ID")
    private Integer sendId;
    /** 发送人账号 */
    @ApiModelProperty(value = "发送人账号")
    private String sendAccount;
    /** 接收人ID */
    @ApiModelProperty(value = "接收人ID")
    private Integer receiveId;
    /** 接收人账号 */
    @ApiModelProperty(value = "接收人账号")
    private String receiveAccount;
    /** 消息内容 */
    @ApiModelProperty(value = "消息内容")
    private String content;
    /** 消息内容 */
    @ApiModelProperty(value = "消息内容")
    private String msgid;
    /** 是否已读（0：否 1：是） */
    @ApiModelProperty(value = "是否已读（0：否 1：是）")
    private int isRead;
    /** 是否删除（0：否 1：是） */
    @ApiModelProperty(value = "是否删除（0：否 1：是）")
    private int isDel;
    /** 是否撤回（0：否 1：是） */
    @ApiModelProperty(value = "是否撤回（0：否 1：是）")
    private int isBack;
    /** 消息类型（text：文字，file：文件，voice：语音，video：视频） */
    @ApiModelProperty(value = "消息类型（text：文字，file：文件，voice：语音，video：视频）")
    private String messageType;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getFriendMessageId() {
        return friendMessageId;
    }

    public void setFriendMessageId(Integer friendMessageId) {
        this.friendMessageId = friendMessageId;
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

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
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

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsBack() {
        return isBack;
    }

    public void setIsBack(int isBack) {
        this.isBack = isBack;
    }
}
