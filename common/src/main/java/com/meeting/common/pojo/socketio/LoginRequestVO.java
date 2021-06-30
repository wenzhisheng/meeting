package com.meeting.common.pojo.socketio;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-06-14 14:44
 * @className com.meeting.common.pojo.socketio.LoginRequest
 */
public class LoginRequestVO implements Serializable {

    private static final long serialVersionUID = 5624438488456712448L;

    /** 账号 */
    private String account;
    /** 密码 */
    private String password;
    /** 秘钥 */
    @ApiModelProperty(value="秘钥")
    private String secretKey;
    /** 内容 */
    private String content;
    /** 会话ID */
    private String sessionId;

    @Override
    public String toString() {
        return "LoginRequestVO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", content='" + content + '\'' +
                ", sessionId='" + sessionId + '\'' +
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
