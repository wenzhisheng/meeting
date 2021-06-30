package com.meeting.common.config.rocketmq;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: 自定义MQ消息异常类
 */
public class RocketMQException extends RuntimeException{

    public RocketMQException() {
        super();
    }

    public RocketMQException(String message) {
        super(message);
    }

    public RocketMQException(String message, Throwable cause) {
        super(message, cause);
    }

    public RocketMQException(Throwable cause) {
        super(cause);
    }

    protected RocketMQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
