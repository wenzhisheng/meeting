package com.meeting.common.pojo.admin;

import com.meeting.common.pojo.base.AdminBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: dameizi
 * @description: 管理员
 * @dateTime 2019-03-26 22:56
 * @className MerchantVO
 */
@ApiModel(value="管理员",description="管理员")
public class AdminVO extends AdminBaseVO implements Serializable {

    private static final long serialVersionUID = 115643297315826400L;
    /** 账号ID */
    @ApiModelProperty(value="账号ID")
    private Integer adminId;
    /** 账号名称 */
    @ApiModelProperty(value="账号")
    private String account;
    /** 账号密码 */
    @ApiModelProperty(value="密码")
    private String password;
    /** 旧密码 */
    @ApiModelProperty(value="旧密码")
    private String oldPassword;
    /** 确认密码 */
    @ApiModelProperty(value="确认密码")
    private String confirmPassword;
    /** 手机号码 */
    @ApiModelProperty(value="手机号码")
    private String telphone;
    /** 邮箱 */
    @ApiModelProperty(value="邮箱")
    private String email;
    /** 秘钥 */
    @ApiModelProperty(value="秘钥")
    private String secretKey;
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value="是否启用（0：禁用 1：启用）")
    private Integer isEnable;

    /** 旧手机号码 */
    @ApiModelProperty(value="旧手机号码")
    private String oldTelPhone;
    /** 手机验证码 */
    @ApiModelProperty(value="手机验证码")
    private String phoneSmsCode;
    /** 账号类型 */
    @ApiModelProperty(value="账号类型")
    private String type;
    /** 当前权限 */
    @ApiModelProperty(value="当前权限")
    private String currentAuthority;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getOldTelPhone() {
        return oldTelPhone;
    }

    public void setOldTelPhone(String oldTelPhone) {
        this.oldTelPhone = oldTelPhone;
    }

    public String getPhoneSmsCode() {
        return phoneSmsCode;
    }

    public void setPhoneSmsCode(String phoneSmsCode) {
        this.phoneSmsCode = phoneSmsCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrentAuthority() {
        return currentAuthority;
    }

    public void setCurrentAuthority(String currentAuthority) {
        this.currentAuthority = currentAuthority;
    }
}
