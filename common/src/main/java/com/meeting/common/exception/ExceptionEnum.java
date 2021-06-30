package com.meeting.common.exception;

/**
 * @dateTime 2019-03-26 17:53
 * @author: dameizi
 * @description: 异常枚举，正常状态1
 */
public enum ExceptionEnum {

    PARAM_ERROR(0,"请求参数非法！"),
    UNKNOW_ERROR(-1,"系统出现异常！"),
    SQL_SYNTAX_ERROR(-2,"数据库语法异常！"),
    DATA_ALREADY_EXIST(-3,"数据已经存在！"),
    UNKNOW_DOMAIN_NAME(-4,"请求的域名无效！"),
    NOT_LOGIN_ERROR(401,"账号已在其他地方登陆,请重新登录");

    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
