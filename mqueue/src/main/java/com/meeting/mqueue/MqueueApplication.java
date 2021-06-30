package com.meeting.mqueue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// Servlet扫描
@ServletComponentScan
// 指定根路径扫描
@ComponentScan(basePackages={"com.meeting.mqueue","com.meeting.common"})
// 指定dao扫描
@MapperScan(basePackages={"com.meeting.mqueue.**.dao",
        "com.meeting.common.**.dao",
        "com.baomidou.mybatisplus.samples.quickstart.mapper"})
public class MqueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqueueApplication.class, args);
    }

}
