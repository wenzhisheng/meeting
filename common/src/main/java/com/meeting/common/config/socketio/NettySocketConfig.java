package com.meeting.common.config.socketio;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;

/**
 * @author dameizi
 * @description NettySocket
 * @dateTime 2019-06-14 14:28
 * @className com.meeting.common.config.socketio.NettySocketConfig
 */
@Configuration
public class NettySocketConfig {

    /** windows服务器地址 */
    @Value("${netty.socket.hostWindows}")
    private String hostIP;
    /** linux服务器地址 */
    @Value("${netty.socket.host}")
    private String host;
    /** 端口 */
    @Value("${netty.socket.port}")
    private int port;
    /** 自定义协议秘钥 */
    @Value("${netty.socket.authorizedCode}")
    private String authorizedCode;
    /** 协议升级超时时间（毫秒） */
    @Value("${netty.socket.upgradeTimeout}")
    private int upgradeTimeout;
    /** Ping消息间隔（毫秒） */
    @Value("${netty.socket.pingInterval}")
    private int pingInterval;
    /** Ping消息超时时间（毫秒） */
    @Value("${netty.socket.pingTimeout}")
    private int pingTimeout;
    /** 工作线程 */
    @Value("${netty.socket.workerThreads}")
    private int workerThreads;
    /** 指挥线程 */
    @Value("${netty.socket.bossThreads}")
    private int bossThreads;

    @Bean(value = "SocketIOServer")
    public SocketIOServer socketIOServer() {
        // 创建Socket，并设置监听端口
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        // 设置主机名，默认是0.0.0.0
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().startsWith("windows")){
            config.setHostname(this.hostIP);
        }else{
            config.setHostname(this.host);
        }
        // 设置监听端口
        config.setPort(this.port);
        // 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(this.upgradeTimeout);
        // Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(this.pingInterval);
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(this.pingTimeout);
        // 设置为最大数据帧的长度
        config.setMaxFramePayloadLength(65536*2);
        // 设置消息最大长度
        config.setMaxHttpContentLength(1024*1024);
        // 获取并发事件调用
        config.setAckMode(AckMode.MANUAL);
        // 然后在每个事件调用中创建一个单独的线程，然后在线程完成时调用ackRequest
        config.setWorkerThreads(this.workerThreads);
        config.setBossThreads(this.bossThreads);
        //config.setTransports(Transport.WEBSOCKET);
        // 握手协议参数使用JWT的Token认证方案
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                //ws://127.0.0.1:8918?account=test&password=test
                // 例如果使用如上参数connection，可以使用如下代码获取用户密码信息
                //String account = data.getSingleUrlParam("account");
                //String password = data.getSingleUrlParam("password");
                //if (!data.getSingleUrlParam("authorizedCode").equals(authorizedCode)){
                //return false;
                //}
                return true;
            }
        });
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

}
