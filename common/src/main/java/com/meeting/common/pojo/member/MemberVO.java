package com.meeting.common.pojo.member;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 成员信息
 * @dateTime 2019-06-11 14:26
 * @className MemberVO
 */
@ApiModel(value="成员信息",description="成员信息")
public class MemberVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -2384110528181282647L;
    /** 会员主键ID */
    @ApiModelProperty(value = "会员主键ID")
    private Integer memberId;
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String account;
    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password;
    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /** 旧密码 */
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;
    /** 确认密码 */
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;
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
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1：启用）")
    private Integer isEnable;
    /** 角色类型（1：会员 2：管理员） */
    @ApiModelProperty(value = "角色类型（1：会员 2：管理员）")
    private int roleType;
    /** 登录时间 */
    @ApiModelProperty(value="登录时间")
    private Date gmtLogin;

    /** 会话ID */
    @ApiModelProperty(value="会话ID")
    private String sessionId;
    /** 登录时间差 */
    @ApiModelProperty(value="登录时间差")
    private String difftime;

    /** 是否是好友（no：不是，yes：是，oneself：自己） */
    @ApiModelProperty(value="是否是好友（no：不是，yes：是，oneself：自己）")
    private String isFriend;

    /** 验证码 */
    @ApiModelProperty(value="验证码")
    private String authCode;

    /** 找回类型（email：邮箱，telephone：手机号） */
    @ApiModelProperty(value="找回类型（email：邮箱，telephone：手机号）")
    private String findType;

    /** 申请状态（0：申请中 1：已同意 2：已过期） */
    @ApiModelProperty(value = "申请状态（0：申请中 1：已同意 2：已过期）")
    private Integer status;
    /** 备注信息 */
    @ApiModelProperty(value = "备注信息")
    private String remark;
    /** 申请时间 */
    @ApiModelProperty(value="申请时间")
    private Date applicationDate;

    /** 群组ID */
    @ApiModelProperty(value = "群组ID")
    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getFindType() {
        return findType;
    }

    public void setFindType(String findType) {
        this.findType = findType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDifftime() {
        return difftime;
    }

    public void setDifftime(String difftime) {
        this.difftime = difftime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public Date getGmtLogin() {
        return gmtLogin;
    }

    public void setGmtLogin(Date gmtLogin) {
        this.gmtLogin = gmtLogin;
    }
}
