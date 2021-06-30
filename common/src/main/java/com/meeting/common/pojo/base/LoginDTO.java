package com.meeting.common.pojo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 登录信息
 * @dateTime 2019-05-27 16:13
 * @className com.meeting.common.pojo.login.LoginVO
 */
@ApiModel(value="登录信息", description="登录信息")
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -5760105326577574164L;
    /** 账号 */
    @ApiModelProperty(value="账号",required=true)
    private String account;
    /** 密码 */
    @ApiModelProperty(value="密码",required=true)
    private String password;
    /** 秘钥 */
    @ApiModelProperty(value="秘钥")
    private String secretKey;
    /** 验证码 */
    @ApiModelProperty(value="验证码")
    private String validCode;

    @Override
    public String toString() {
        return "LoginVO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", validCode='" + validCode + '\'' +
                '}';
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
}
