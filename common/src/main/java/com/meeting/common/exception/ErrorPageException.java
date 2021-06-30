package com.meeting.common.exception;

/**
 * @dateTime 2019-03-26 17:53
 * @author: dameizi
 * @description: 错误页异常
 */
public class ErrorPageException extends RuntimeException{

    private Integer code;

    /**
     * @author: dameizi
     * @dateTime: 2019/3/26 0026 下午 18:01
     * @description: 继承exception，加入错误状态值
     * @param: [exceptionEnum]
     * @return:
     */
    public ErrorPageException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/26 0026 下午 18:01
     * @description: 自定义错误信息
     * @param: [message, code]
     * @return:
     */
    public ErrorPageException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
