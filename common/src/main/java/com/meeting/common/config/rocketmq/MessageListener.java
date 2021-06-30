package com.meeting.common.config.rocketmq;

import com.meeting.common.util.SpringContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: RocketMQ消息监听
 */
public class MessageListener implements MessageListenerConcurrently {

    private static final Logger logger = LogManager.getLogger(MessageListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt msg : msgs){
            String errorInfo;
            try {
                MessageProcessor messageProcessor = (MessageProcessor) SpringContextHolder.getBean(msg.getTags());
                logger.info("Tag=>{}", msg.getTags());
                errorInfo = messageProcessor.handleMessage(msg);
            } catch (Exception e) {
                // 记录报错信息
                errorInfo = e.getMessage();
            }
            // 错误信息不为空
            if (!StringUtils.isEmpty(errorInfo)){
                // 记录错误信息
                logger.error("Key为 {} 的消息处理失败：{}", msg.getKeys(), errorInfo);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
