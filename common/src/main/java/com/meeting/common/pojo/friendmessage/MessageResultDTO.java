package com.meeting.common.pojo.friendmessage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dameizi
 * @description 消息结果数据
 * @dateTime 2019-07-21 13:03
 * @className com.meeting.meeting.pojo.FriendMessageVO
 */
@ApiModel(value = "MessageResultDTO", description = "消息结果数据")
public class MessageResultDTO implements Serializable {

    private static final long serialVersionUID = 708603496182457022L;
    /** 消息ID */
    @ApiModelProperty(value = "消息ID")
    private Integer messageId;
    /** 发送人ID */
    @ApiModelProperty(value = "发送人ID")
    private Integer sendId;
    /** 发送人账号 */
    @ApiModelProperty(value = "发送人账号")
    private String sendAccount;
    /** 发送人头像 */
    @ApiModelProperty(value = "发送人头像")
    private String sendAvatar;
    /** 发送人名称 */
    @ApiModelProperty(value = "发送人名称")
    private String sendNickname;
    /** 接收人ID */
    @ApiModelProperty(value = "接收人ID")
    private Integer receiveId;
    /** 接收人账号 */
    @ApiModelProperty(value = "接收人账号")
    private String receiveAccount;
    /** 接收人头像 */
    @ApiModelProperty(value = "接收人头像")
    private String receiveAvatar;
    /** 接收人名称 */
    @ApiModelProperty(value = "接收人名称")
    private String receiveNickname;
    /** 消息内容 */
    @ApiModelProperty(value = "消息内容")
    private String content;
    /** 消息标识 */
    @ApiModelProperty(value = "消息标识")
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
    /** 创建时间 */
    @ApiModelProperty(value="创建时间", hidden=true)
    private Date gmtCreate;
    /** 修改时间 */
    @ApiModelProperty(value="修改时间", hidden=true)
    private Date gmtModified;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSendAvatar() {
        return sendAvatar;
    }

    public void setSendAvatar(String sendAvatar) {
        this.sendAvatar = sendAvatar;
    }

    public String getSendNickname() {
        return sendNickname;
    }

    public void setSendNickname(String sendNickname) {
        this.sendNickname = sendNickname;
    }

    public String getReceiveAvatar() {
        return receiveAvatar;
    }

    public void setReceiveAvatar(String receiveAvatar) {
        this.receiveAvatar = receiveAvatar;
    }

    public String getReceiveNickname() {
        return receiveNickname;
    }

    public void setReceiveNickname(String receiveNickname) {
        this.receiveNickname = receiveNickname;
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
