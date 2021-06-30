package com.meeting.common.enums;

/**
 * @author: dameizi
 * @description: 状态码
 * @dateTime 2019-03-29 20:26
 * @className StatusCode
 */
public enum StatusCode {

    /**
     * 未登录,未通过认证
     */
    STATUS_401(401),
    /**
     * IP非法访问
     */
    STATUS_402(402),
    /**
     * 没权限非法访问
     */
    STATUS_403(403),
    /**
     * 上下文路径请求错误
     */
    STATUS_404(404),
    /**
     * 需要安全认证
     */
    STATUS_405(405),
    /**
     * 密码异常
     */
    STATUS_406(406);

    /**
     * 返回类型
     */
    private Integer status;

    StatusCode(int i) {
        this.status = i;
    }

    public Integer getStatus() {
        return status;
    }

}
