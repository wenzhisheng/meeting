package com.meeting.common.config.rocketmq;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: RocketMQ生产者配置
 */
@Configuration
public class ProducerConfiguration {

    public static final Logger logger = LogManager.getLogger(ProducerConfiguration.class);

    @Value("${rocketmq.productor.groupName:null}")
    private String groupName;
    @Value("${rocketmq.productor.namesrvAddr:null}")
    private String namesrvAddr;
    @Value("${rocketmq.productor.instanceName:null}")
    private String instanceName;
    @Value("${rocketmq.productor.maxMessageSize:131072}")
    private int maxMessageSize;
    @Value("${rocketmq.productor.sendMsgTimeout:10000}")
    private int sendMsgTimeout;
    @Value("${application.start.mq.productor:true}")
    private boolean start;

    @Bean(value = "rocketMQProducer")
    public DefaultMQProducer getRocketMQProducer() throws RocketMQException {
        // 判断是否启动生产者
        if(!start) {
            return null;
        }
        if (StringUtils.isBlank(this.groupName) || "null".equals(this.groupName)) {
            throw new RocketMQException("groupName is blank");
        }
        if (StringUtils.isBlank(this.namesrvAddr) || "null".equals(this.namesrvAddr)) {
            throw new RocketMQException("nameServerAddr is blank");
        }
        if (StringUtils.isBlank(this.instanceName) || "null".equals(this.instanceName)){
            throw new RocketMQException("instanceName is blank");
        }
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);
        producer.setNamesrvAddr(this.namesrvAddr);
        producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        try {
            producer.start();
            logger.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , this.groupName, this.namesrvAddr));
        } catch (MQClientException e) {
            logger.error(String.format("producer is error [%s]", e.getMessage()));
            throw new RocketMQException(e);
        }
        return producer;
    }
}

