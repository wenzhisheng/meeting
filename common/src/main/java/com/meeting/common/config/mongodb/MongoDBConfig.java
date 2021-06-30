package com.meeting.common.config.mongodb;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

/**
 * @author dameizi
 * @description MongDB配置
 * @dateTime 2019-07-12 12:24
 * @className com.meeting.common.areacascade.dao.IAreaCascadeDao
 */
@Configuration
public class MongoDBConfig {

    /** IP */
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;
    /** 端口 */
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    /** 数据库 */
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;

    @Bean(value = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(mongoHost, mongoPort), mongoDatabase);
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        // tell mongodb to use the custom converters
        mongoMapping.setCustomConversions(customConversions());
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }

    /**
     * Returns the list of custom converters that will be used by the MongoDB template
     **/
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(new DoubleToBigDecimalConverter(),
                new BigDecimalToDoubleConverter()));
    }

}