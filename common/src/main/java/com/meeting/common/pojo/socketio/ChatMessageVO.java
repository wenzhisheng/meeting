package com.meeting.common.pojo.socketio;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 聊天信息
 * @dateTime 2019-06-14 14:43
 * @className com.meeting.common.pojo.socketio.ChatMessage
 */
public class ChatMessageVO implements Serializable {

    private static final long serialVersionUID = -8791127660006379017L;
    /** 会员ID */
    private Integer memberId;
    /** 会员账号 */
    private String memberAccount;
    /** 会员别名 */
    private String memberAlias;
    /** 会员头像 */
    private String memberAvatar;
    /** 目标ID */
    private Integer targetId;
    /** 目标账号 */
    private String account;
    /** 目标头像 */
    private String avatar;
    /** 目标别名 */
    private String alias;
    /** 消息内容 */
    private String content;
    /** 消息类型(single:个人，cluster:群组，system:系统) */
    private String messageType;
    /** 消息标识 */
    private String msgid;
    /** 客户端ID */
    private String clientId;

    /** 在线状态：online:在线，hide:隐身，offline:离线 */
    private String status;

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public String getMemberAlias() {
        return memberAlias;
    }

    public void setMemberAlias(String memberAlias) {
        this.memberAlias = memberAlias;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
