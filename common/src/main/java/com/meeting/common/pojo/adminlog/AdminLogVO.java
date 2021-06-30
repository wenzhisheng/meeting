package com.meeting.common.pojo.adminlog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meeting.common.pojo.base.AdminBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: dameizi
 * @description:  管理员登录日志
 * @dateTime 2019-04-01 13:11
 * @className com.meeting.common.pojo.adminloginlog.AdminLoginLog
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "管理员登录日志", description = "管理员登录日志")
public class AdminLogVO extends AdminBaseVO implements Serializable {

    private static final long serialVersionUID = 8232541932059438165L;

    /** 管理员登录日志Id */
    @ApiModelProperty(value = "登录日志ID")
    private BigInteger logId;
    /** 管理员账号 */
    @ApiModelProperty(value = "管理员账号")
    private String account;
    /** 登录IP */
    @ApiModelProperty(value = "登录IP")
    private String loginIp;
    /** 登录真实地址 */
    @ApiModelProperty(value = "登录真实地址")
    private String loginRealAddress;
    /** 登录时间 */
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    public BigInteger getLogId() {
        return logId;
    }

    public void setLogId(BigInteger logId) {
        this.logId = logId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginRealAddress() {
        return loginRealAddress;
    }

    public void setLoginRealAddress(String loginRealAddress) {
        this.loginRealAddress = loginRealAddress;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
