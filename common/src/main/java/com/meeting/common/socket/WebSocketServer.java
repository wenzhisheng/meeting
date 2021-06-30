//package com.meeting.common.socket;
//
//import com.meeting.common.util.ApplicationContextUtil;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class WebSocketServer {
//
//    private final static Logger logger = LogManager.getLogger(WebSocketServer.class);
//
//    private String nettyPort = "8918";
//
//    public void startServer(){
//        // 获取配置文件中的websocket监听端口, 有时会获取不到
//        // String nettyPort = ApplicationContextUtil.getApplicationContext().getEnvironment().getProperty("websocket.inet.port");
//        Integer inetPort = Integer.valueOf(nettyPort);
//        //服务端需要2个线程组  boss处理客户端连接  worker进行客服端连接之后的处理
//        EventLoopGroup boss = new NioEventLoopGroup();
//        EventLoopGroup work = new NioEventLoopGroup();
//        try {
//            ServerBootstrap bootstrap = new ServerBootstrap();
//            //服务器 配置
//            bootstrap.group(boss, work);
//            //指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
//            bootstrap.channel(NioServerSocketChannel.class);
//            //设置子通道也就是SocketChannel的处理器
//            bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
//                @Override
//                protected void initChannel(SocketChannel socketChannel) throws Exception {
//                    // HttpServerCodec：将请求和应答消息解码为HTTP消息
//                    socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
//                    // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
//                    socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
//                    // ChunkedWriteHandler：向客户端发送HTML5文件
//                    socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
//                    // 配置通道处理  来进行业务处理
//                    socketChannel.pipeline().addLast("handler", new WebSocketServerHandler());
//                }
//            });
//            logger.info("服务端开启端口号[{}]监听，等待客户端连接..", inetPort);
//            // 绑定端口  开启事件侦听驱动
//            Channel channel = bootstrap.bind(inetPort).sync().channel();
//            channel.closeFuture().sync();
//        } catch (Exception e){
//            logger.error(e);
//        } finally {
//            //关闭资源
//            boss.shutdownGracefully();
//            work.shutdownGracefully();
//        }
//    }
//
//    /**
//     * PostConstruct注解是标注spring启动时启动的注解
//     */
//    @PostConstruct
//    public void init(){
//        //new Thread(() -> new WebSocketServer().startServer()).start();
//    }
//
//}
