//package com.meeting.common.websocket;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.meeting.common.constant.RedisKeyConst;
//import com.meeting.common.util.DateUtil;
//import com.meeting.common.util.SpringContextHolder;
//import org.redisson.api.RBucket;
//import org.redisson.api.RList;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.text.MessageFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//@ServerEndpoint(value = "/ws")
//public class WS {
//
//	/** Log4j日志 */
//	private static Logger logger = LoggerFactory.getLogger(WS.class);
//	/** 获取redission客户端连接 */
//	private RedissonClient redissonClient = SpringContextHolder.getBean("redissonClient");
//    /** ConcurrentHashMap是线程安全的，而HashMap是线程不安全的 */
//    private static ConcurrentHashMap<String, Session> mapUS = new ConcurrentHashMap<String, Session>();
//    /** US(UserSession),SU(SessionUser) */
//    private static ConcurrentHashMap<Session, String> mapSU = new ConcurrentHashMap<Session, String>();
//
//    /**
//     * @author: dameizi
//     * @dateTime: 2019-06-13 14:06
//     * @description: 建立连接成功
//     * @param: [session, account]
//     * @return: void
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("account") String account) {
//    	// 如果为空表示心跳检查
//		if (StringUtils.isEmpty(account)){
//			account = "ping";
//		}
//		String jsonResult = setNoticeMessage(account, "online");
//		// 循环发给所有在线的人，上线通知
//		for(Session s: session.getOpenSessions()){
//			s.getAsyncRemote().sendText(jsonResult);
//		}
//		// 设置在线放到MAP
//		this.setOnlinePutMap(session, account);
//	}
//
//	/**
//	 * @author: dameizi
//	 * @dateTime: 2019-06-13 15:21
//	 * @description: 通知消息
//	 * @param: [account, status]
//	 * @return: java.lang.String
//	 */
//	private String setNoticeMessage(String account, String status) {
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("content", status);
//		jsonObject.put("account", account);
//		jsonObject.put("type", "onlineStatus");
//		return JSONObject.toJSONString(jsonObject);
//	}
//
//	/**
//	 * @author: dameizi
//	 * @dateTime: 2019-06-13 14:07
//	 * @description: 设置在线放到MAP
//	 * @param: [session, account]
//	 * @return: void
//	 */
//	private void setOnlinePutMap(Session session, String account) {
//		mapUS.put(account, session);
//		mapSU.put(session, account);
//		//更新redis中的会员在线状态
//		String onlineRedisKey = MessageFormat.format(RedisKeyConst.MEETING_ONLINE_STATUS, account);
//		RBucket<String> bucket = redissonClient.getBucket(onlineRedisKey);
//		bucket.set("online");
//		logger.info(String.format("%s进入聊天,当前在线人数为%d", account, mapUS.size()));
//	}
//
//	/**
//	 * @author: dameizi
//	 * @dateTime: 2019-06-13 14:48
//	 * @description: 连接关闭调用
//	 * @param: [session]
//	 * @return: void
//	 */
//	@OnClose
//	public void onClose(Session session) {
//		String account = mapSU.get(session);
//		if (!StringUtils.isEmpty(account)) {
//			// 下线通知,循环发给所有在线的人
//			String jsonResult = this.setNoticeMessage(account, "offline");
//			for(Session se:session.getOpenSessions()){
//				se.getAsyncRemote().sendText(jsonResult);
//			}
//			// 设置离线且移除MAP
//			this.setOfflineRemoveMap(session, account);
//		}
//	}
//
//	/**
//	 * @author: dameizi
//	 * @dateTime: 2019-06-13 14:00
//	 * @description: 发生错误时调用
//	 * @param: [session, error]
//	 * @return: void
//	 */
//	@OnError
//	public void onError(Session session, Throwable error) {
//		String account = mapSU.get(session);
//		if (!StringUtils.isEmpty(account)) {
//			// 设置离线且移除MAP
//			this.setOfflineRemoveMap(session, account);
//		}
//		logger.error("WebSocket发生错误! {}", error.getStackTrace());
//	}
//
//	/**
//	 * @author: dameizi
//	 * @dateTime: 2019-06-13 14:05
//	 * @description: 设置离线且移除MAP
//	 * @param: [session, account]
//	 * @return: void
//	 */
//	private void setOfflineRemoveMap(Session session, String account) {
//		String onlineRedisKey = MessageFormat.format(RedisKeyConst.MEETING_ONLINE_STATUS, account);
//		RBucket<String> bucket = redissonClient.getBucket(onlineRedisKey);
//		bucket.set("offline");
//		mapUS.remove(account);
//		mapSU.remove(session);
//		logger.info(String.format("%s退出聊天,当前在线人数为%d", account, mapUS.size()));
//	}
//
//	/**
//	 * @author: dameizi
//	 * @dateTime: 2019-06-13 15:26
//	 * @description: 收到客户端消息后调用
//	 * @param: [message, session]
//	 * @return: void
//	 */
//    @OnMessage
//    public void onMessage(String message, Session session){
//		if(message.equals("ping")){
//			session.getAsyncRemote().sendText("pong");
//		}else{
//			JSONObject jsonObject = JSONObject.parseObject(message);
//			String type = jsonObject.getJSONObject("to").getString("type");
//			if(type.equals("onlineStatus")){
//				// 循环发给所有在线的人
//				this.sendAllOnline(session, jsonObject, type);
//			}else if(type.equals("recallDeleteMsg")){
//				// 三分钟之内撤回与删除消息
//				this.recallDeleteMsg(jsonObject);
//			}else if(type.equals("sendMessage")){
//				// 发送消息
//				this.sendMessage(session, jsonObject, type);
//			}
//		}
//    }
//
//    /**
//     * @author: dameizi
//     * @dateTime: 2019-06-13 17:21
//     * @description: 发送消息
//     * @param: [session, jsonObject, type]
//     * @return: void
//     */
//	private void sendMessage(Session session, JSONObject jsonObject, String type) {
//		String toAccount = jsonObject.getJSONObject("to").getString("account");
//		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//		Date date = new Date();
//		String time=df.format(date);
//		jsonObject.put("time", time);
//		JSONObject toMessage = new JSONObject();
//		toMessage.put("avatar", jsonObject.getJSONObject("mine").getString("avatar"));
//		toMessage.put("type",type);
//		toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));
//		toMessage.put("timestamp", date.getTime());
//		toMessage.put("msgid",jsonObject.getJSONObject("mine").getString("msgid"));
//		toMessage.put("time",time);
//		toMessage.put("mine",false);
//		toMessage.put("account",jsonObject.getJSONObject("mine").getString("account"));
//		if(type.equals("friend")||type.equals("fankui")){
//			toMessage.put("account", jsonObject.getJSONObject("mine").getString("account"));
//			toMessage.put("toAccount", jsonObject.getJSONObject("mine").getString("account"));
//		}else{
//			toMessage.put("account", jsonObject.getJSONObject("to").getString("account"));
//			toMessage.put("toAccount", jsonObject.getJSONObject("mine").getString("account"));
//		}
//		switch (type) {
//			//单聊,记录到mongo
//			case "friend":
//				//如果在线，及时推送,发送消息给对方
//				if(mapUS.containsKey(toAccount)){
//					mapUS.get(toAccount).getAsyncRemote().sendText(toMessage.toString());
//					logger.info("单聊的消息:{}",toMessage.toString() );
//				}else{
//					// 如果不在线 就记录到Reidis数据库，下次对方上线时推送给对方。
//					String messageListKey = MessageFormat.format(RedisKeyConst.MESSAGE_LIST, toAccount);
//					RList rList = redissonClient.getList(messageListKey);
//					rList.add(toMessage.toString());
//					logger.info("单聊对方不在线，消息已存入缓存数据库{}", toMessage.toString());
//				}
//				break;
//			//家长与老师反馈,记录到mongo
//			case "fankui":
//				//如果在线，及时推送
//				if(mapUS.containsKey(toAccount)){
//					//发送消息给对方
//					mapUS.get(toAccount).getAsyncRemote().sendText(toMessage.toString());
//					logger.info("反馈的消息:{}", toMessage.toString());
//				}else{
//					String messageListKey = MessageFormat.format(RedisKeyConst.MESSAGE_LIST, toAccount);
//					RList rList = redissonClient.getList(messageListKey);
//					rList.add(toMessage.toString());
//					logger.info("反馈对方不在线，消息已存入缓存数据库{}", toMessage.toString());
//				}
//				break;
//			case "group":
//				//获取群成员account列表
//				JSONArray memberList=jsonObject.getJSONObject("to").getJSONArray("memberList");
//				if(memberList.size() > 0){
//					//发送到在线用户(除了发送者)
//					for(int i=0;i<memberList.size();i++){
//						if(mapUS.containsKey(memberList.get(i)) && !memberList.get(i).equals(jsonObject.getJSONObject("mine").getString("account"))){
//							synchronized(session){
//								try {
//									session = mapUS.get(memberList.get(i));
//									session.getAsyncRemote().sendText(toMessage.toString());
//									logger.info("群聊的消息:{}", toMessage.toString());
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//							}
//						}else if(memberList.get(i).equals(jsonObject.getJSONObject("mine").getString("account"))){
//							   // 如果是发送者自己，不做任何操作。
//						   }else{
//							// 如果是离线用户,数据存到redis待用户上线后推送。
//							String messageListKey = MessageFormat.format(RedisKeyConst.MESSAGE_LIST, toAccount);
//							RList rList = redissonClient.getList(messageListKey);
//							rList.add(toMessage.toString());
//						   }
//					}
//				}
//				String messageListKey = MessageFormat.format(RedisKeyConst.MESSAGE_QUN_LAST_TIME, toAccount);
//				RBucket<Object> bucket = redissonClient.getBucket(messageListKey);
//				bucket.set(DateUtil.dateToString2(new Date()));
//				break;
//			default:
//				break;
//		}
//	}
//
//	/**
//     * @author: dameizi
//     * @dateTime: 2019-06-13 17:20
//     * @description: 三分钟之类撤回消息
//     * @param: [jsonObject]
//     * @return: void
//     */
//	private void recallDeleteMsg(JSONObject jsonObject) {
//		Session session;
//		String toType = jsonObject.getJSONObject("to").getString("toType");
//		String toAccount = jsonObject.getJSONObject("to").getString("account");
//		String msgid = jsonObject.getJSONObject("to").getString("msgid");
//		String account = jsonObject.getJSONObject("mine").getString("account");
//		JSONObject toMessage = new JSONObject();
//		toMessage.put("msgid", msgid);
//		toMessage.put("account", account);
//		toMessage.put("toType", toType);
//		toMessage.put("toAccount", toAccount);
//		toMessage.put("type", "delMsg");
//		if(toType!=null && toAccount!=null && msgid!=null){
//			switch (toType) {
//				// 单聊撤回
//				case "friend":
//					// 如果在线，及时推送撤回消息
//					if(mapUS.containsKey(toAccount)){
//						mapUS.get(toAccount).getAsyncRemote().sendText(toMessage.toString());               //发送消息给对方
//						logger.info("撤回单聊的消息{}", toMessage.toString());
//					}else{
//						// 如果是离线用户,删除保存到redis的数据
//						String messageListKey = MessageFormat.format(RedisKeyConst.MESSAGE_LIST, toAccount);
//						RList rList = redissonClient.getList(messageListKey);
//						for (int i=0; i<rList.size(); i++){
//							String str = rList.get(i).toString();
//							if (str.indexOf(msgid) > -1){
//								rList.remove(i);
//								break;
//							}
//						}
//					}
//					break;
//				//家长与老师反馈消息撤回
//				case "fankui":
//					//如果在线，及时推送撤回消息
//					if(mapUS.containsKey(toAccount)){
//						//发送消息给对方
//						mapUS.get(toAccount).getAsyncRemote().sendText(toMessage.toString());
//						logger.info("撤回反馈的消息:{}", toMessage.toString());
//					}else{
//						// 如果是离线用户,删除保存到redis的数据
//						String messageListKey = MessageFormat.format(RedisKeyConst.MESSAGE_LIST, toAccount);
//						RList rList = redissonClient.getList(messageListKey);
//						for (Object o : rList){
//							String s = o.toString();
//							if (s.indexOf(msgid) > -1){
//								rList.remove(s);
//								break;
//							}
//						}
//					}
//					break;
//				case "group":
//					//获取群成员account列表
//					JSONArray memberList = jsonObject.getJSONObject("to").getJSONArray("memberList");
//					if(memberList.size() > 0){
//						//发送到在线用户(除了发送者)
//						for(int i=0; i<memberList.size(); i++){
//							if(mapUS.containsKey(memberList.get(i)) && !memberList.get(i).equals(jsonObject.getJSONObject("mine").get("account"))){
//								session=mapUS.get(memberList.get(i));
//								session.getAsyncRemote().sendText(toMessage.toString());
//								logger.info("撤回群聊的消息:{}", toMessage.toString());
//							}
//						}
//					}
//					break;
//				default:
//					break;
//			}
//		}
//	}
//
//	/**
//     * @author: dameizi
//     * @dateTime: 2019-06-13 17:18
//     * @description: 循环发给所有在线的人
//     * @param: [session, jsonObject, type]
//     * @return: void
//     */
//	private void sendAllOnline(Session session, JSONObject jsonObject, String type) {
//		for(Session s:session.getOpenSessions()){
//			JSONObject toMessage = new JSONObject();
//			toMessage.put("account", jsonObject.getJSONObject("mine").getString("account"));
//			toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));
//			toMessage.put("type", type);
//			s.getAsyncRemote().sendText(toMessage.toString());
//		}
//	}
//
//    /**
//     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
//     */
//    public void sendMessage(Session session, String message) {
//           session.getAsyncRemote().sendText(message);
//    }
//
//}
