package com.meeting.common.pojo.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 成员对话信息
 * @dateTime 2019-06-11 14:26
 * @className MemberVO
 */
@ApiModel(value="成员对话信息",description="成员对话信息")
public class MemberDialogDTO implements Serializable {

    private static final long serialVersionUID = 3417723754938846398L;
    /** 会员主键ID */
    @ApiModelProperty(value = "会员主键ID")
    private Integer memberId;
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String account;
    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /** 出生日期 */
    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern="yyyy-MM-dd", iso=DateTimeFormat.ISO.DATE)
    private Date birthday;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /** 头像 */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /** 地区 */
    @ApiModelProperty(value = "地区")
    private String region;
    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    private String telephone;
    /** 个性签名 */
    @ApiModelProperty(value = "个性签名")
    private String motto;
    /** 性别（0：保密 1：男 2：女） */
    @ApiModelProperty(value = "性别（0：保密 1：男 2：女）")
    private Integer gender;
    /** 强提醒（no：正常，yes：强提醒） */
    @ApiModelProperty(value = "强提醒（no：正常，yes：强提醒）")
    private String strongReminder;
    /** 聊天置顶（no：不置顶，yes：正常） */
    @ApiModelProperty(value = "聊天置顶（no：不置顶，yes：正常）")
    private String chatTop;
    /** 是否打扰（no：不打扰，yes：正常） */
    @ApiModelProperty(value = "是否打扰（no：不打扰，yes：正常）")
    private String isDisturb;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getStrongReminder() {
        return strongReminder;
    }

    public void setStrongReminder(String strongReminder) {
        this.strongReminder = strongReminder;
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
