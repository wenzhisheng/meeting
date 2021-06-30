package com.meeting.mqueue.processor;

import com.meeting.common.config.rocketmq.MessageProcessor;
import com.meeting.common.constant.RedisKeyConst;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author: dameizi
 * @description: 支付延时失效消费
 * @dateTime 2019-04-09 21:46
 * @className com.meeting.mqueue.processor.Payement
 */
@Component("payment_delay_time_tag")
public class Payement implements MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(Payement.class);

    @Override
    public String handleMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        logger.info("000：" + message);
        String[] arr = message.split("-");
        if(StringUtils.isNotBlank(message)){

        }
        return null;
    }

}
