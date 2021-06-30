//package com.meeting.common.socket;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.codec.http.websocketx.*;
//import io.netty.util.CharsetUtil;
//import io.netty.util.concurrent.GlobalEventExecutor;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
//
//    private final static Logger logger = LogManager.getLogger(WebSocketServerHandler.class);
//
//    private WebSocketServerHandshaker webSocketServerHandshaker;
//
//    /**
//     * @author: dameizi
//     * @dateTime: 2019-04-14 21:10
//     * @description: 存储每一个客户端接入进来的对象
//     */
//    public static ChannelGroup allGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    //存储群Id和通道关系
//    public static Map<String, Channel> groupMap = new HashMap<>();
//
//    public WebSocketServerHandler() {
//        super();
//    }
//
//    /**
//     * 客户端与服务端建立了通信通道并且可以传输数据
//     * @param context
//     */
//    @Override
//    public void channelActive(ChannelHandlerContext context) {
//        // 添加到通道Group中
//        allGroup.add(context.channel());
//        logger.info("客户端与服务端连接开启：" + context.channel().remoteAddress());
//    }
//
//    /**
//     * 客户端与服务端关闭了通信通道并且不可以传输数据
//     * @param context
//     */
//    @Override
//    public void channelInactive(ChannelHandlerContext context) {
//        allGroup.remove(context.channel());
//        // 移除channel
//        this.removeChannel(context);
//        logger.info("客户端与服务端连接关闭：" + context.channel().remoteAddress());
//    }
//
//    /**
//     * 服务端接收客户端发送过来的数据结束之后调用
//     * @param context
//     * @throws Exception
//     */
//    @Override
//    public void channelReadComplete(ChannelHandlerContext context) {
//        context.flush();
//    }
//
//    /**
//     * 出现异常的时候调用
//     * @param context
//     * @param throwable
//     */
//    @Override
//    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) {
//        logger.error("【系统异常】" + throwable);
////        context.close();
//    }
//
//    /**
//     * 移除缓存中的通道信息
//     * @param context
//     */
//    private void removeChannel(ChannelHandlerContext context) {
//        allGroup.remove(context.channel());
//    }
//
//    /**
//     * 处理客户端websocke请求
//     * @param context
//     * @param msg
//     */
//    @Override
//    protected void channelRead0(ChannelHandlerContext context, Object msg) {
//        //处理客户端向服务端发起的http握手请求
//        if (msg instanceof FullHttpRequest) {
//            handleHttpRequest(context, (FullHttpRequest) msg);
//            //处理websocket链接业务
//        } else if (msg instanceof WebSocketFrame) {
//            handleWebSocketFrame(context, (WebSocketFrame) msg);
//        }
//    }
//
//    /**
//     * 处理客户端向服务端发起http握手请求业务
//     * @param context
//     * @param fullHttpRequest
//     */
//    private void handleHttpRequest(ChannelHandlerContext context, FullHttpRequest fullHttpRequest) {
//        // 如果HTTP解码失败，返回HHTP异常
//        if (!fullHttpRequest.decoderResult().isSuccess() || !("websocket".equals(fullHttpRequest.headers().get("Upgrade")))) {
//            sendHttpResponse(context, fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//            return;
//        }
//        //HttpMethod method = fullHttpRequest.method();
//        String uri = fullHttpRequest.uri();
//        // 校验websocket的请求路径是否正确，ws:ip:port/member
//        String key = uri.substring(6);
//        groupMap.put(key, context.channel());
//        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
//                "ws://" + fullHttpRequest.headers().get(HttpHeaderNames.HOST) + uri, null, false);
//        webSocketServerHandshaker = wsFactory.newHandshaker(fullHttpRequest);
//        if (webSocketServerHandshaker == null) {
//            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
//        } else {
//            webSocketServerHandshaker.handshake(context.channel(), fullHttpRequest);
//        }
//    }
//
//    /**
//     * 服务端向客户端发送响应消息
//     * @param context
//     * @param fullHttpRequest
//     * @param defaultFullHttpResponse
//     */
//    private void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest fullHttpRequest, DefaultFullHttpResponse defaultFullHttpResponse) {
//        if (defaultFullHttpResponse.status().code() != 200) {
//            ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.status().toString(), CharsetUtil.UTF_8);
//            defaultFullHttpResponse.content().writeBytes(buf);
//            buf.release();
//        }
//        //服务端向客户端发送数据
//        ChannelFuture future = context.channel().writeAndFlush(defaultFullHttpResponse);
//        if (defaultFullHttpResponse.status().code() != 200) {
//            future.addListener(ChannelFutureListener.CLOSE);
//        }
//    }
//
//    /**
//     * 处理客户端与服务端之间的websocket业务
//     * @param context
//     * @param webSocketFrame
//     */
//    private void handleWebSocketFrame(ChannelHandlerContext context, WebSocketFrame webSocketFrame) {
//        // 判断是否是关闭websocket的指令
//        if (webSocketFrame instanceof CloseWebSocketFrame) {
//            webSocketServerHandshaker.close(context.channel(), (CloseWebSocketFrame) webSocketFrame.retain());
//            return;
//        }
//        //判断是否是ping消息
//        if (webSocketFrame instanceof PingWebSocketFrame) {
//            context.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
//            return;
//        }
//        if (!(webSocketFrame instanceof TextWebSocketFrame)) {
//            // 暂时仅支持文本消息
//            logger.error("暂时仅支持此类文本消息 -> " + webSocketFrame.getClass());
//            throw new UnsupportedOperationException(String.format("%s types not supported", webSocketFrame.getClass().getName()));
//        } else {
//            logger.info("netty msg：{}",((TextWebSocketFrame) webSocketFrame).text());
//            TextWebSocketFrame tws = new TextWebSocketFrame("Send Message Body");
//            allGroup.writeAndFlush(tws);
//        }
//    }
//
//}
