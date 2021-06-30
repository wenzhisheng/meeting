package com.meeting.common.config.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author dameizi
 * @description SpringBoot启动之后执行
 * @dateTime 2019-06-14 14:46
 * @className com.meeting.common.config.socketio.ServerRunner
 */
@Component
@Order(1)
public class ServerRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public ServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) {
        server.startAsync();
    }

}
