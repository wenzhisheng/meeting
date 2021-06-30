package com.meeting.common.config.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: RocketMQ消息处理
 */
public interface MessageProcessor {

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 17:16
     * @description: 处理消息的接口
     * @param: [messageExt]
     * @return: java.lang.String  返回错误信息
     */
    String handleMessage(MessageExt messageExt);

}
