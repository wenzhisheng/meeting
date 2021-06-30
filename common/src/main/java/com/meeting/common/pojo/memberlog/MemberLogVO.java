package com.meeting.common.pojo.memberlog;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 会员登录日志
 * @dateTime 2019-06-01 13:00
 * @className com.meeting.common.pojo.memberlog.MemberLogVO
 */
@ApiModel(value = "会员登录日志", description = "会员登录日志")
public class MemberLogVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 5202084967990964620L;

    /** 登录日志ID */
    @ApiModelProperty(value = "登录日志ID")
    private BigInteger loginLogId;
    /** 会员账号 */
    @ApiModelProperty(value = "会员账号")
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

    public BigInteger getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(BigInteger loginLogId) {
        this.loginLogId = loginLogId;
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
