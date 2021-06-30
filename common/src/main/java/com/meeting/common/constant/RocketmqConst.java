package com.meeting.common.constant;

/**
 * @author: dameizi
 * @description: 消息队列常量
 * @dateTime 2019-03-26 23:57
 * @className com.meeting.common.exception.OtherReturn
 */
public class RocketmqConst {

    /**消息延期级别*/
    public static final int MQ_DELAY_TIME_LEVEL = 6;

    /** 订单延时失效 */
    public static final String MQ_PAYMENT_DELAY_TIME_GROUPNAME = "payment_delay_time_group";
    public static final String MQ_PAYMENT_DELAY_TIME_TOPIC = "payment_delay_time_topic";
    public static final String MQ_PAYMENT_DELAY_TIME_TAG = "payment_delay_time_tag";

}
