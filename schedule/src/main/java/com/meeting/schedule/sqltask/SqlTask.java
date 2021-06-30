package com.meeting.schedule.sqltask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: dameizi
 * @description: 数据库任务
 * @dateTime 2019-04-09 18:20
 * @className com.meeting.schedule.sqltask.SqlTask
 */
@Component
public class SqlTask {

    public static final Logger logger = LoggerFactory.getLogger(SqlTask.class);
    
    @Scheduled(cron = "0 0 1 * * ?")
    public void configureTasks(){
        logger.info("0000000000000000000");
    }

}
