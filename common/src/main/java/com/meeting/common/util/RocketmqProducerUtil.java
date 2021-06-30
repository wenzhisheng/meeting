package com.meeting.common.util;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dameizi
 * @description Rocketmq工具类
 * @dateTime 2019-06-06 14:32
 * @className RocketmqProducerUtil
 */
public class RocketmqProducerUtil {

    private static final Logger logger = LoggerFactory.getLogger(RocketmqProducerUtil.class);

    /** 通过上下文获取MQ生产者 */
    private static DefaultMQProducer defaultMQProducer = SpringContextHolder.getBean("rocketMQProducer");


}
