//package com.meeting.common.socket;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.ConnectListener;
//import com.corundumstudio.socketio.listener.DataListener;
//import com.corundumstudio.socketio.listener.DisconnectListener;
//import com.meeting.common.member.service.IMemberService;
//import com.meeting.common.util.SpringContextHolder;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.websocket.Session;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArrayList;
//
///**
// * @author dameizi
// * @description TODO
// * @dateTime 2019-06-14 12:22
// * @className com.meeting.common.socket.SocketioServer
// */
//@Service
//public class SocketioServer {
//
//    private static final Logger logger = LoggerFactory.getLogger(SocketioServer.class);
//
//    /** socketio服务配置 */
//    private String localhost = "10.16.28.20";
//    private Integer socketioPort = 8918;
//
//    // 用于保存所有客户端，线程安全的List
//    private static List<SocketIOClient> clientsList = new CopyOnWriteArrayList<SocketIOClient>();
//
//    public void startServer(){
//        Configuration config = new Configuration();
//        config.setHostname(localhost);
//        config.setPort(socketioPort);
//        SocketIOServer server = new SocketIOServer(config);
//        // 添加客户端连接监听器
//        server.addConnectListener(new ConnectListener() {
//            @Override
//            public void onConnect(SocketIOClient client) {
//                //不知道如何与客户端对应，好的办法是自己去写对应的函数
//                client.sendEvent("connected", "hello");
//                clientsList.add(client);
//                logger.info("{}客户端建立连接", client.getRemoteAddress());
//            }
//        });
//        // 监听客户端事件，welcomeIn为事件名称，自定义事件
//        server.addEventListener("welcomeIn", String.class, new DataListener<String>(){
//            @Override
//            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws ClassNotFoundException {
//                //客户端推送welcomeHome事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型
//                String sa = client.getRemoteAddress().toString();
//                //获取客户端连接的ip
//                String clientIp = sa.substring(1,sa.indexOf(":"));
//                //获取客户端url参数
//                Map params = client.getHandshakeData().getUrlParams();
//                logger.info("{}客户端发送数据{}", clientIp, data);
//                logger.info("{}客户端发送数据2{}", clientIp, params);
//            }
//        });
//        // 添加客户端断开连接事件
//        server.addDisconnectListener(new DisconnectListener(){
//            @Override
//            public void onDisconnect(SocketIOClient client) {
//                String sa = client.getRemoteAddress().toString();
//                //获取设备ip
//                String clientIp = sa.substring(1,sa.indexOf(":"));
//                //给客户端发送消息
//                client.sendEvent("welcomeHome",String.format("%d welcome home,good bye", clientIp));
//                clientsList.add(client);
//                logger.info("{}客户端已断开连接", clientIp);
//            }
//        });
//        server.start();
//        // 发送消息
//        while (true){
//            try {
//                Thread.sleep(1500);
//                //广播消息
//                server.getBroadcastOperations().sendEvent("borcast","are you live?");
//            } catch (InterruptedException e) {
//                logger.info("广播消息失败{}", e);
//            }
//        }
//    }
//
//    // SpringBoot启动初始化socketio
//    @PostConstruct
//    public void init(){
//        //new Thread(() -> new SocketioServer().startServer()).start();
//    }
//
//}
