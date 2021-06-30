package com.meeting.common.config.socketio;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastAckCallback;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.meeting.common.application.service.IApplicationService;
import com.meeting.common.areacascade.service.IAreaCascadeService;
import com.meeting.common.baseconfig.service.IBaseConfigService;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.dialog.service.IDialogService;
import com.meeting.common.exception.ResultUtil;
import com.meeting.common.friend.service.IFriendService;
import com.meeting.common.friendapply.service.IFriendApplyService;
import com.meeting.common.friendmessage.service.IFriendMessageService;
import com.meeting.common.group.service.IGroupService;
import com.meeting.common.groupmember.service.IGroupMemberService;
import com.meeting.common.groupmessage.service.IGroupMessageService;
import com.meeting.common.member.service.IMemberService;
import com.meeting.common.navigation.service.INavigationService;
import com.meeting.common.pojo.application.ApplicationVO;
import com.meeting.common.pojo.areacascade.AreaCascadeVO;
import com.meeting.common.pojo.baseconfig.BaseConfigVO;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import com.meeting.common.pojo.friendmessage.MessageResultDTO;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import com.meeting.common.pojo.groupmessage.GroupMessageVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.navigation.NavigationVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.pojo.socketio.HistoryMessageVO;
import com.meeting.common.pojo.socketio.LoginRequestVO;
import com.meeting.common.util.CommonUtil;
import com.meeting.common.util.POJOConvertUtil;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.meeting.common.config.socketio.CheckParameter.*;
import static com.meeting.common.init.DaoInit.saveInitFriendMessage;
import static com.meeting.common.init.DaoInit.saveInitGroupMessage;

/**
 * @author dameizi
 * @description socketio处理器
 * @dateTime 2019-06-14 14:41
 * @className com.meeting.common.config.socketio.MessageEventHandler
 */
@Component
public class MessageEventHandler {

    /** Log4j日志 */
    private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);
    /** ConcurrentHashMap是线程安全的，而HashMap是线程不安全的，SU(SessionidUser) */
    public static ConcurrentHashMap<String, String> mapSU = new ConcurrentHashMap<String, String>();
    /** inject server */
    private final SocketIOServer server;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IMemberService iMemberService;
    @Autowired
    private INavigationService iNavigationService;
    @Autowired
    private IApplicationService iApplicationService;
    @Autowired
    private IFriendService iFriendService;
    @Autowired
    private IGroupService iGroupService;
    @Autowired
    private IFriendMessageService iFriendMessageService;
    @Autowired
    private IGroupMessageService iGroupMessageService;
    @Autowired
    private IGroupMemberService iGroupMemberService;
    @Autowired
    private IDialogService iDialogService;
    @Autowired
    private IFriendApplyService iFriendApplyService;
    @Autowired
    private IAreaCascadeService iAreaCascadeService;
    @Autowired
    private IBaseConfigService iBaseConfigService;
    @Autowired
    public MessageEventHandler(SocketIOServer socketIOServer) {
        this.server = socketIOServer;
    }

    /** 当客户端发起连接时调用 */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if(client != null) {
            String sessionId = client.getSessionId().toString();
            logger.info("连接成功,clientSessionId={}", sessionId);
        } else {
            logger.error("连接为空");
        }
    }

    /** 客户端断开连接时调用，刷新客户端信息 */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        // 断开连接清空在线连接存储
        String account = CommonConst.STRING_NULL;
        if (mapSU != null && mapSU.size() != 0){
            String sessionId = client.getSessionId().toString();
            account = mapSU.get(sessionId);
            mapSU.remove(sessionId);
            // 设置缓存在线状态、登录信息、客户端sessionid，根据登录账号，用于发送到目标者使用
            String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, account);
            RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
            clientMap.put(RedisKeyConst.MEETING_ONLINE_STATUS, CommonConst.OFFLINE_ONLINE);
            clientMap.expire(15, TimeUnit.DAYS);
            // 设置缓存登录信息，根据客户端ID
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if(clientSession != null){
                clientSession.delete();
            }
        }
        client.disconnect();
        logger.info("{}账号下线断开,clientSessionId={}", account, client.getSessionId().toString());
    }

    /** 历史消息 */
    @OnEvent(value = "historyMessage")
    public void onHistoryMessage(SocketIOClient client, AckRequest ackRequest, HistoryMessageVO message) {
        // 参数校验正确才进行操作
        if (!historyMessage(client, message)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null){
                MemberVO memberVO = clientSession.get();
                message.setSendId(memberVO.getMemberId());
                IPage<MessageResultDTO> historyMessage = iFriendMessageService.getHistoryMessage(message);
                client.sendEvent("historyMessageResult", ResultUtil.success(historyMessage));
            }else{
                client.sendEvent("historyMessageResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 最新30消息记录 */
    @OnEvent(value = "listMessage")
    public void onMessageList(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // 参数校验正确才进行操作
        if (!listMessage(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                // 最新30条单聊消息
                chatMessageVO.setMemberId(clientSession.get().getMemberId());
                List<MessageResultDTO> historyMessage = iFriendMessageService.listMessage(chatMessageVO);
                // 回发消息列表
                client.sendEvent("listMessageResult", ResultUtil.success(historyMessage));
            } else {
                client.sendEvent("listMessageResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 对话更多信息(单聊获取最新时间，群聊获取群总数) */
    @OnEvent(value = "dialogMoreInfo")
    public void onDialogMoreInfo(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // 参数校验正确才进行操作
        if (!listMessage(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                chatMessageVO.setMemberId(clientSession.get().getMemberId());
                // 回发消息列表
                client.sendEvent("dialogMoreInfoResult", ResultUtil.success(iMemberService.getMemberLoginTime(chatMessageVO)));
            } else {
                client.sendEvent("dialogMoreInfoResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 发送消息 */
    @OnEvent(value = "sendMsg")
    public void onSendMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // 参数检查
        if (!sendMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                RLock fairLock = null;
                try{
                    // 分布式锁控制并发且消息正常
                    fairLock = getrLock(client, memberVO, fairLock);
                    // 消息类型(single:个人，cluster:群组，system:系统)
                    String messageTyle = chatMessageVO.getMessageType();
                    chatMessageVO.setMemberId(memberVO.getMemberId());
                    switch (messageTyle) {
                        case CommonConst.MESSAGE_CLASS_SINGLE:
                            // 单聊消息
                            chatMessageVO.setMemberAccount(memberVO.getAccount());
                            chatMessageVO.setMemberAlias(memberVO.getNickname());
                            chatMessageVO.setMemberAvatar(memberVO.getAvatar());
                            singleSendMsg(client, chatMessageVO, memberVO);
                            break;
                        case CommonConst.MESSAGE_CLASS_CLUSTER:
                            // 群聊消息
                            clusterSendMsg(client, chatMessageVO, memberVO);
                            break;
                        case CommonConst.MESSAGE_CLASS_SYSTEM:
                            // 系统消息
                            break;
                        default:
                            client.sendEvent("listMessageResult", ResultUtil.error(0, "消息类型错误"));
                            break;
                    }
                } finally {
                    if (fairLock != null) {
                        fairLock.unlock();
                    }
                }
            }
        }
    }

    /** redisson锁 */
    private RLock getrLock(SocketIOClient client, MemberVO memberVO, RLock fairLock) {
        String friendFairLock = MessageFormat.format("{0}{1}", client.getSessionId(), memberVO.getAccount());
        fairLock = redissonClient.getFairLock(friendFairLock);
        fairLock.lock();
        return fairLock;
    }

    /** 群聊发送消息 */
    private void clusterSendMsg(SocketIOClient client, ChatMessageVO chatMessageVO, MemberVO memberVO) {
        chatMessageVO.setAccount(chatMessageVO.getAccount());
        // 生成消息唯一标识
        chatMessageVO.setMsgid(CommonUtil.generateOrderNo(memberVO.getMemberId()));
        // 保存群聊
        saveGroupMessage(chatMessageVO, memberVO);
        // 保存对话
        iDialogService.save(chatMessageVO);
        // 最新30条群聊消息，分页聊天记录
        List<MessageResultDTO> listMessage = iGroupMessageService.listHistoryMessage(chatMessageVO);
        // 发送房间群内广播
        server.getRoomOperations(chatMessageVO.getAccount()).sendEvent("listMessageResult", listMessage, new BroadcastAckCallback<String>(String.class) {
            @Override
            protected void onClientTimeout(SocketIOClient client) {
                logger.info("{}发送群聊消息{}", chatMessageVO.getAlias() , "超时了");
            }
            @Override
            protected void onClientSuccess(SocketIOClient client, String result) {
                logger.info("{}发送群聊消息{}", chatMessageVO.getAlias(), "已接到");
            }
        });
        // 回发消息
        singleAndClusterSend(client, memberVO, listMessage);
        logger.info("会员{}向群组{}发送消息:{}", memberVO.getAccount(), chatMessageVO.getAccount(), chatMessageVO.getContent());
    }

    private List<DialogVO> singleAndClusterSend(SocketIOClient client, MemberVO memberVO, List<MessageResultDTO> listMessage) {
        // 对话列表
        DialogVO dialogVO = new DialogVO();
        dialogVO.setMemberId(memberVO.getMemberId());
        List<DialogVO> listDialog = iDialogService.listDialog(dialogVO);
        // 回发消息列表，对话列表
        client.sendEvent("listMessageResult", ResultUtil.success(listMessage));
        client.sendEvent("dialogueListResult", ResultUtil.success(listDialog));
        return listDialog;
    }

    private void singleSendMsg(SocketIOClient client, ChatMessageVO chatMessageVO, MemberVO memberVO) {
        // 生成消息唯一标识
        chatMessageVO.setMsgid(CommonUtil.generateOrderNo(memberVO.getMemberId()));
        // 保存单聊记录
        saveFriendMessage(chatMessageVO, memberVO);
        // 保存对话
        iDialogService.save(chatMessageVO);
        // 分页聊天记录
        List<MessageResultDTO> listMessage = iFriendMessageService.listMessage(chatMessageVO);
        // 回发消息
        List<DialogVO> listDialog = singleAndClusterSend(client, memberVO, listMessage);
        // 发送消息
        sendMessageList(chatMessageVO, listMessage, listDialog);
        logger.info("会员{}向联系人{}发送消息:{}", memberVO.getAccount(), chatMessageVO.getAccount(), chatMessageVO.getContent());
    }

    /** 读取消息 */
    @OnEvent(value = "readMsg")
    public void onReadMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        logger.info("向联系人{}读取消息", chatMessageVO.getTargetId());
        // 参数检测
        if (!readMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                String messageTyle = chatMessageVO.getMessageType();
                // 消息类型(single:个人，cluster:群组，system:系统)
                chatMessageVO.setMemberId(memberVO.getMemberId());
                switch (messageTyle) {
                    case CommonConst.MESSAGE_CLASS_SINGLE:
                        // 单聊读取消息
                        singleAndClusterReadMsg(client, chatMessageVO, memberVO);
                        logger.info("会员{}向联系人{}读取消息", memberVO.getAccount(), chatMessageVO.getTargetId());
                        break;
                    case CommonConst.MESSAGE_CLASS_CLUSTER:
                        // 群聊读取消息
                        singleAndClusterReadMsg(client, chatMessageVO, memberVO);
                        logger.info("会员{}向群组{}读取消息", memberVO.getAccount(), chatMessageVO.getTargetId());
                        break;
                    default:
                        client.sendEvent("listMessageResult", ResultUtil.error(0, "消息类型错误"));
                        break;
                }
            }
        }
    }

    private void singleAndClusterReadMsg(SocketIOClient client, ChatMessageVO chatMessageVO, MemberVO memberVO) {
        // 已读消息
        iDialogService.updateUnread(chatMessageVO);
        // 更新回发对话列表
        DialogVO dialogVO = new DialogVO();
        dialogVO.setMemberId(memberVO.getMemberId());
        client.sendEvent("dialogueListResult", ResultUtil.success(iDialogService.listDialog(dialogVO)));
    }

    /** 撤回消息 */
    @OnEvent(value = "recallMsg")
    public void onRecallMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // 发送聊天基础参数
        if (!deleteMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                // 消息类型(single:个人，cluster:群组，system:系统)
                String messageTyle = chatMessageVO.getMessageType();
                chatMessageVO.setMemberId(memberVO.getMemberId());
                switch (messageTyle) {
                    case CommonConst.MESSAGE_CLASS_SINGLE:
                        // 单聊撤回消息
                        recallFriendMessage(chatMessageVO);
                        // 发送和回发
                        snedAndCallback(client, chatMessageVO);
                        logger.info("会员{}向联系人{}撤回消息:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    case CommonConst.MESSAGE_CLASS_CLUSTER:
                        // 群聊撤回消息
                        recallGroupMessage(chatMessageVO);
                        // 发送和回发
                        snedAndCallback(client, chatMessageVO);
                        logger.info("会员{}向群组{}撤回消息:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    default:
                        client.sendEvent("listMessageResult", ResultUtil.error(0, "消息类型错误"));
                        break;
                }
            }
        }
    }

    private void snedAndCallback(SocketIOClient client, ChatMessageVO chatMessageVO) {
        // 更新对话
        DialogVO dialogVO = new DialogVO();
        dialogVO.setMemberId(chatMessageVO.getMemberId());
        List<DialogVO> listDialog = iDialogService.listDialog(dialogVO);
        // 分页聊天记录
        List<MessageResultDTO> listMessage = iFriendMessageService.listMessage(chatMessageVO);
        // 回发消息列表，对话列表
        client.sendEvent("listMessageResult", ResultUtil.success(listMessage));
        client.sendEvent("dialogueListResult", ResultUtil.success(listDialog));
        // 发送消息列表
        dialogVO.setMemberId(chatMessageVO.getMemberId());
        sendMessageList(chatMessageVO, listMessage, iDialogService.listDialog(dialogVO));
    }

    /** 删除消息 */
    @OnEvent(value = "deleteMsg")
    public void onDeleteMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // 发送聊天基础参数
        if (!deleteMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                // 消息类型(single:个人，cluster:群组，system:系统)
                String messageTyle = chatMessageVO.getMessageType();
                chatMessageVO.setMemberId(memberVO.getMemberId());
                switch (messageTyle) {
                    case CommonConst.MESSAGE_CLASS_SINGLE:
                        // 单聊删除消息
                        deleteFriendMessage(chatMessageVO);
                        // 发送和回发
                        snedAndCallback(client, chatMessageVO);
                        logger.info("会员{}向群组{}删除消息:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    case CommonConst.MESSAGE_CLASS_CLUSTER:
                        // 群聊删除消息
                        deleteGroupMessage(chatMessageVO);
                        // 发送和回发
                        snedAndCallback(client, chatMessageVO);
                        logger.info("会员{}向群组{}删除消息:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    default:
                        client.sendEvent("listMessageResult", ResultUtil.error(0, "消息类型错误"));
                        break;
                }
            }
        }
    }

    /** 发送列表消息 */
    private void sendMessageList(ChatMessageVO chatMessageVO, List<MessageResultDTO> listMessage, List<DialogVO> listDialog) {
        // 获取用户缓存数据
        String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, chatMessageVO.getAccount());
        RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
        if (clientMap != null && clientMap.size() != 0) {
            // 如果离线消息保存到redis,上线后立即发送
            if (CommonConst.OFFLINE_ONLINE.equals(clientMap.get(RedisKeyConst.MEETING_ONLINE_STATUS))) {
                String offlineMessageKey = MessageFormat.format(RedisKeyConst.MEETING_OFFLINE_MESSAGE, chatMessageVO.getAccount());
                RList<ChatMessageVO> list = redissonClient.getList(offlineMessageKey);
                list.add(chatMessageVO);
            } else {
                UUID sessionid = (UUID) clientMap.get(RedisKeyConst.MEETING_SOCKETIO_UUID);
                server.getClient(sessionid).sendEvent("listMessageResult", ResultUtil.success(listMessage));
                server.getClient(sessionid).sendEvent("dialogueListResult", ResultUtil.success(listDialog));
            }
        }
    }

    /** 已读列表消息 */
    private void readMessageList(String account, List<DialogVO> listDialog) {
        // 获取用户缓存数据
        String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, account);
        RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
        if (clientMap != null && clientMap.size() != 0) {
            // 如果在线则推送，否则不比推送，上线自动获取最新
            if (CommonConst.ONLINE_OFFLINE.equals(clientMap.get(RedisKeyConst.MEETING_ONLINE_STATUS))) {
                UUID sessionid = (UUID) clientMap.get(RedisKeyConst.MEETING_SOCKETIO_UUID);
                server.getClient(sessionid).sendEvent("dialogueListResult", ResultUtil.success(listDialog));
            }
        }
    }

    /** 单聊撤回消息 */
    private void recallFriendMessage(ChatMessageVO chatMessageVO) {
        FriendMessageVO friendMessageVO = new FriendMessageVO();
        friendMessageVO.setIsBack(1);
        friendMessageVO.setMsgid(chatMessageVO.getMsgid());
        iFriendMessageService.readOrRecallOrDelete(friendMessageVO);
    }

    /** 群聊撤回消息 */
    private void recallGroupMessage(ChatMessageVO chatMessageVO) {
        GroupMessageVO groupMessageVO = new GroupMessageVO();
        groupMessageVO.setIsBack(1);
        groupMessageVO.setMsgid(chatMessageVO.getMsgid());
        iGroupMessageService.readOrRecallOrDelete(groupMessageVO);
    }

    /** 单聊删除消息 */
    private void deleteFriendMessage(ChatMessageVO chatMessageVO) {
        FriendMessageVO friendMessageVO = new FriendMessageVO();
        friendMessageVO.setIsDel(1);
        friendMessageVO.setMsgid(chatMessageVO.getMsgid());
        iFriendMessageService.readOrRecallOrDelete(friendMessageVO);
    }

    /** 群聊删除消息 */
    private void deleteGroupMessage(ChatMessageVO chatMessageVO) {
        GroupMessageVO groupMessageVO = new GroupMessageVO();
        groupMessageVO.setIsDel(1);
        groupMessageVO.setMsgid(chatMessageVO.getMsgid());
        iGroupMessageService.readOrRecallOrDelete(groupMessageVO);
    }

    /** 保存单聊 */
    private void saveFriendMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        FriendMessageVO friendMessageVO = saveInitFriendMessage(chatMessageVO, memberVO);
        friendMessageVO.setMessageType("text");
        iFriendMessageService.save(friendMessageVO);
    }

    /** 保存群聊 */
    private void saveGroupMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        GroupMessageVO groupMessageVO = saveInitGroupMessage(chatMessageVO, memberVO);
        groupMessageVO.setMessageType("text");
        iGroupMessageService.save(groupMessageVO);
    }

    /** 登录 */
    @OnEvent(value = "login")
    public void onLogin(SocketIOClient client, AckRequest ackRequest, LoginRequestVO loginRequestVO) {
        //if(ackRequest.isAckRequested()) {
            //ackRequest.sendAckData("login success");
        //}
        // DTO转为VO
        MemberVO memberVO = POJOConvertUtil.convertPojo(loginRequestVO, MemberVO.class);
        if (StringUtils.isEmpty(memberVO.getAccount()) && StringUtils.isEmpty(memberVO.getPassword())){
            client.sendEvent("loginResult", ResultUtil.error(0,"账号密码不能为空"));
        }
        // 判断用户登录
        String sessionId = client.getSessionId().toString();
        MemberVO memberVO1 = iMemberService.login(memberVO);
        if (memberVO1 != null){
            memberVO1.setSessionId(sessionId);
            // 保存客户端连接账号
            String account = memberVO1.getAccount();
            mapSU.put(sessionId, account);
            // 缓存登录信息
            cacheLoginInfo(client, memberVO1);
            // 通知客户端消息，用于当前客户端使用
            client.sendEvent("loginResult", ResultUtil.success(memberVO1));
            // 更新登录时间
            iMemberService.updateLoginTime(memberVO1);
            // 发送离线消息
            sendOfflineMessage(client, memberVO1);
            // 获取群成员列表，关联所在的群组初始房间
            List<GroupVO> groupList = iGroupService.list(memberVO1);
            for (GroupVO groupVO : groupList) {
                client.joinRoom(groupVO.getAccount());
            }
            logger.info("{}账号上线登录,clientSessionId={}", loginRequestVO.getAccount(), client.getSessionId());
        }else{
            client.sendEvent("loginResult", ResultUtil.error(0,"账号或密码错误"));
        }
    }

    /** 缓存登录信息 */
    private void cacheLoginInfo(SocketIOClient client, MemberVO memberVO1) {
        String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, memberVO1.getAccount());
        RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
        // 单点登陆控制
        if(clientMap != null || CommonConst.ONLINE_OFFLINE.equals(clientMap.get(RedisKeyConst.MEETING_ONLINE_STATUS))){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, clientMap.get(RedisKeyConst.MEETING_SOCKETIO_UUID));
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            clientSession.delete();
        }
        // 缓存在线状态、客户端uuid，根据登录账号，用于发送到目标者使用
        clientMap.put(RedisKeyConst.MEETING_SOCKETIO_UUID, client.getSessionId());
        clientMap.put(RedisKeyConst.MEETING_ONLINE_STATUS, CommonConst.ONLINE_OFFLINE);
        // 缓存登录信息，根据客户端ID
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        clientSession.set(memberVO1);
    }

    /** 发送离线消息 */
    private void sendOfflineMessage(SocketIOClient client, MemberVO memberVO1) {
        // 是否有离线消息,有则一条一条发
        String offlineMessageKey = MessageFormat.format(RedisKeyConst.MEETING_OFFLINE_MESSAGE, memberVO1.getAccount());
        RList<ChatMessageVO> messageList = redissonClient.getList(offlineMessageKey);
        if (messageList != null && messageList.size() != 0){
            for(ChatMessageVO chatMessageVO: messageList){
                // 获取最近30条消息记录，回发消息列表
                chatMessageVO.setMemberId(memberVO1.getMemberId());
                client.sendEvent("listMessageMsg", ResultUtil.success(iFriendMessageService.listMessage(chatMessageVO)));
            }
            // 发送完清空离线消息
            messageList.delete();
        }
    }

    /** 菜单栏接口 */
    @OnEvent(value = "menus")
    public void onMenus(SocketIOClient client, AckRequest ackRequest) {
        client.sendEvent("menusList", ResultUtil.success(iNavigationService.list(new NavigationVO())));
    }

    /** 地区省 */
    @OnEvent(value = "areaProvince")
    public void onAreaProvince(SocketIOClient client, AckRequest ackRequest) {
        client.sendEvent("areaProvinceResult", ResultUtil.success(iAreaCascadeService.provinceList()));
    }

    /** 地区市 */
    @OnEvent(value = "areaCityCounty")
    public void onAreaCityCounty(SocketIOClient client, AckRequest ackRequest, AreaCascadeVO areaCascadeVO) {
        if (!CheckParameter.areaCityCounty(client, areaCascadeVO)) {
            client.sendEvent("areaCityCountyResult", ResultUtil.success(iAreaCascadeService.cityList(areaCascadeVO)));
        }
    }

    /** 地区县 */
    @OnEvent(value = "areaCounty")
    public void onAreaCounty(SocketIOClient client, AckRequest ackRequest, AreaCascadeVO areaCascadeVO) {
        if (!CheckParameter.areaCounty(client, areaCascadeVO)) {
            client.sendEvent("areaCountyResult", ResultUtil.success(iAreaCascadeService.cityList(areaCascadeVO)));
        }
    }

    /** 级联地区 */
    @OnEvent(value = "areaCascade")
    public void onAreaCascade(SocketIOClient client, AckRequest ackRequest) {
        client.sendEvent("areaCascadeResult", ResultUtil.success(iAreaCascadeService.cascadeList()));
    }

    /** 所有应用 */
    @OnEvent(value = "application")
    public void onApplication(SocketIOClient client, AckRequest ackRequest) {
        logger.info("333333333333333333333333333333333333");
        client.sendEvent("applicationList", ResultUtil.success(iApplicationService.list(new ApplicationVO())));
    }

    /** 我的应用 */
    @OnEvent(value = "myApplication")
    public void onMyApplication(SocketIOClient client, AckRequest ackRequest) {
        logger.info("22222222222222222222222222222222222");
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            ApplicationVO applicationVO = new ApplicationVO();
            applicationVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("myApplicationResult", ResultUtil.success(iApplicationService.list(applicationVO)));
        } else {
            client.sendEvent("myApplicationResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 搜索为添加 */
    @OnEvent(value = "searchToAdd")
    public void onSearchToAdd(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            friendVO.setMemberId(clientSession.get().getMemberId());
            MemberVO memberVO = iMemberService.searchByAccount(friendVO);
            if (memberVO != null){
                friendVO.setTargetId(memberVO.getMemberId());
                // 是否是好友包括删除好友
                if (iFriendService.isDeleteFriend(friendVO)){
                    memberVO.setIsFriend("yes");
                }else if(memberVO.getMemberId().equals(clientSession.get().getMemberId())){
                    memberVO.setIsFriend("oneself");
                } else {
                    memberVO.setIsFriend("no");
                }
                client.sendEvent("searchToAddResult", ResultUtil.success(memberVO));
            }else{
                client.sendEvent("searchToAddResult", ResultUtil.success("账号不存在"));
            }
        } else {
            client.sendEvent("searchToAddResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 添加好友 */
    @OnEvent(value = "addFriend")
    public void onAddFriend(SocketIOClient client, AckRequest ackRequest, FriendApplyVO friendApplyVO) {
        // 参数校验正确才进行操作
        if (!CheckParameter.addFriend(client, friendApplyVO)) {
            if (StringUtils.isEmpty(friendApplyVO.getRemark())){
                friendApplyVO.setRemark("请求添加你为好友");
            }
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                // 只有目标者才有申请数据
                friendApplyVO.setMemberId(friendApplyVO.getTargetId());
                friendApplyVO.setTargetId(clientSession.get().getMemberId());
                // 好友数量上限
                int maxFriend = iBaseConfigService.getBaseConfig().getMaxFriend();
                int countFriendMeember = iFriendService.countFriendMeember(friendApplyVO);
                int countFriendTarget = iFriendService.countFriendTarget(friendApplyVO);
                // 实际好友数量小于基础配置好友数量才能添加好友
                if (countFriendMeember < maxFriend && countFriendTarget < maxFriend){
                    client.sendEvent("addFriendResult", ResultUtil.success(iMemberService.addFriend(friendApplyVO)));
                } else {
                    client.sendEvent("addFriendResult", ResultUtil.error(0, "好友数量上限"));
                }
            } else {
                client.sendEvent("addFriendResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 申请数量 */
    @OnEvent(value = "applyingCount")
    public void onApplyingCount(SocketIOClient client, AckRequest ackRequest) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            client.sendEvent("applyingCountResult", ResultUtil.success(iFriendApplyService.applyingCount(clientSession.get())));
        } else {
            client.sendEvent("applyingCountResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 同意添加 */
    @OnEvent(value = "agreeAdd")
    public void onAgreeAdd(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        // 参数校验正确才进行操作
        if (!CheckParameter.agreeAdd(client, friendVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                client.sendEvent("agreeAddResult", ResultUtil.success(iMemberService.agreeAdd(clientSession.get(), friendVO)));
            } else {
                client.sendEvent("agreeAddResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 删除好友 */
    @OnEvent(value = "deleteFriend")
    public void onDeleteFriend(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        if (!CheckParameter.deleteFriend(client, friendVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                friendVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("deleteFriendResult", ResultUtil.success(iFriendService.deleteFriend(friendVO)));
            } else {
                client.sendEvent("deleteFriendResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 拉黑好友 */
    @OnEvent(value = "blacklistFriend")
    public void onBlacklistFriend(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        if (!CheckParameter.blacklistFriend(client, friendVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                friendVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("blacklistFriendResult", ResultUtil.success(iFriendService.blacklistFriend(friendVO)));
            } else {
                client.sendEvent("blacklistFriendResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 解除拉黑 */
    @OnEvent(value = "unblock")
    public void onUnblock(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        if (!CheckParameter.unblock(client, friendVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                friendVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("unblockResult", ResultUtil.success(iFriendService.unblock(friendVO)));
            } else {
                client.sendEvent("unblockResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /*public static final int DEFAULT_BUFFER_SIZE = 1024;
    private Selector selector;
    private SocketChannel clientChannel;
    private ByteBuffer buf;
    private boolean isConnected = false;
    public void iiiii(){
        try {
            selector = Selector.open();
            clientChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9000));
            //设置客户端为非阻塞模式
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            buf = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
            //login();
            isConnected = true;
        }catch (IOException ex){
            logger.error("11{}",ex);
        }
    }*/

    /** 修改别名 */
    @OnEvent(value = "editAlias")
    public void onEditAlias(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        if (friendVO.getRemark().length() > 16){
            client.sendEvent("editAliasResult", ResultUtil.error(0, "别名长度太长"));
        }
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            friendVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("editAliasResult", ResultUtil.success(iFriendService.updateAlias(friendVO)));
        }else {
            client.sendEvent("editAliasResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 编辑会员 */
    @OnEvent(value = "editMember")
    public void onEditMember(SocketIOClient client, AckRequest ackRequest, MemberVO memberVO) {
        if (!CheckParameter.editMember(client, memberVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                memberVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("editMemberResult", ResultUtil.success(iMemberService.update(memberVO)));
            } else {
                client.sendEvent("editMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 最新账号 */
    @OnEvent(value = "newMember")
    public void onNnewMember(SocketIOClient client, AckRequest ackRequest) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // 删除旧缓存,数据库获取新数据并返回
            MemberVO memberVO = iMemberService.getMemberById(clientSession.get());
            clientSession.delete();
            clientSession.set(memberVO);
            client.sendEvent("newMemberResult", ResultUtil.success(memberVO));
        } else {
            client.sendEvent("newMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 搜索联系人 */
    @OnEvent(value = "searchLinkman")
    public void onSearchMember(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            friendVO.setMemberId(clientSession.get().getMemberId());
            // list代表列表搜索，class代表分类搜索，根据备注名称模糊搜索得出结果
            if (CommonConst.SEARCH_LIST.equals(friendVO.getSearchType())){
                client.sendEvent("searchLinkmanResult", ResultUtil.success(iFriendService.listFriend(friendVO)));
            }else if (CommonConst.SEARCH_CLASS.equals(friendVO.getSearchType())){
                client.sendEvent("searchLinkmanResult", ResultUtil.success(iFriendService.listClass(friendVO)));
            }
        } else {
            client.sendEvent("searchLinkmanResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 黑名单好友 */
    @OnEvent(value = "blacklistList")
    public void onBlacklistList(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // 判断未传参数，空参数
            if (friendVO == null) {
                friendVO = new FriendVO();
            }
            friendVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("blacklistListResult", ResultUtil.success(iFriendService.blacklistList(friendVO)));
        } else {
            client.sendEvent("blacklistListResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 联系人平铺 */
    @OnEvent(value = "linkmanTiled")
    public void onLinkmanTiled(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // 判断前端对参数误传为空
            if (friendVO == null) {
                friendVO = new FriendVO();
            }
            friendVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("linkmanTiledList", ResultUtil.success(iFriendService.listFriend(friendVO)));
        }else{
            client.sendEvent("linkmanTiledList", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 联系人分类 */
    @OnEvent(value = "linkmanClassify")
    public void onLinkmanClassify(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // 判断前端对参数误传为空
            if (friendVO == null) {
                friendVO = new FriendVO();
            }
            friendVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("linkmanClassifyClass", ResultUtil.success(iFriendService.listClass(friendVO)));
        }else{
            client.sendEvent("linkmanClassifyClass", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 对话列表 */
    @OnEvent(value = "dialogueList")
    public void onDialogueList(SocketIOClient client, AckRequest ackRequest, DialogVO dialogVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (dialogVO == null){
                dialogVO = new DialogVO();
            }
            // 聊天对话列表
            dialogVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("dialogueListResult", ResultUtil.success(iDialogService.listDialog(dialogVO)));
        } else {
            client.sendEvent("dialogueListResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 删除对话 */
    @OnEvent(value = "deleteDialogue")
    public void onDeleteDialogue(SocketIOClient client, AckRequest ackRequest, DialogVO dialogVO) {
        if (!CheckParameter.deleteDialogue(client, dialogVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                // 删除对话且删除聊天记录，并刷新对话列表
                dialogVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("deleteDialogueResult", ResultUtil.success(iDialogService.deleteDialogue(dialogVO)));
                client.sendEvent("dialogueListResult", ResultUtil.success(iDialogService.listDialog(dialogVO)));
                // 发送对话列表
                dialogVO.setMemberId(dialogVO.getTargetId());
                readMessageList(dialogVO.getAccount(), iDialogService.listDialog(dialogVO));
            } else {
                client.sendEvent("deleteDialogueResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 对话信息 */
    @OnEvent(value = "dialogueInfo")
    public void onDialogueInfo(SocketIOClient client, AckRequest ackRequest, DialogVO dialogVO) {
        if (!CheckParameter.dialogueInfo(client, dialogVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                dialogVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("dialogueInfoResult", ResultUtil.success(iMemberService.dialogueInfo(dialogVO)));
            } else {
                client.sendEvent("dialogueInfoResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** 新的好友 */
    @OnEvent(value = "newFriend")
    public void onNewFriend(SocketIOClient client, AckRequest ackRequest) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            client.sendEvent("newFriendList", ResultUtil.success(iMemberService.getNewFriendList(clientSession.get())));
        } else {
            client.sendEvent("newFriendList", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 创建群组 */
    @OnEvent(value = "buildGroup")
    public void onBuildGroup(SocketIOClient client, AckRequest ackRequest, GroupVO groupVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            MemberVO memberVO = clientSession.get();
            groupVO.setMemberId(memberVO.getMemberId());
            groupVO.setMemberAccount(memberVO.getAccount());
            if (!CheckParameter.buildGroup(client, groupVO)) {
                if (checkGroupMemberIds(client, groupVO.getMemberIds(), memberVO.getMemberId(), "buildGroupResult")) {
                    // 群组数量上限
                    BaseConfigVO baseConfig = iBaseConfigService.getBaseConfig();
                    int maxFriend = baseConfig.getMaxGroup();
                    int countGroup = iGroupService.countGroup(groupVO);
                    // 实际创建群组数量小于基础配置创建群组数量才能创建群组
                    if (countGroup < maxFriend && groupVO.getMemberIds().length+1 <= baseConfig.getMaxGroupMember()){
                        client.sendEvent("buildGroupResult", ResultUtil.success(iGroupService.insert(groupVO)));
                    } else {
                        client.sendEvent("buildGroupResult", ResultUtil.error(0, "群组或成员数量上限"));
                    }
                }
            }
        } else {
            client.sendEvent("buildGroupResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 检测群组用户id数组 */
    private boolean checkGroupMemberIds(SocketIOClient client, Integer[] memberIds, Integer memberId, String result) {
        FriendVO friendVO = new FriendVO();
        friendVO.setMemberId(memberId);
        for (int i=0; i<memberIds.length; i++){
            friendVO.setTargetId(memberIds[i]);
            if (!iFriendService.isFriend(friendVO)){
                client.sendEvent(result, ResultUtil.error(0,"群成员ID非法"));
                return false;
            }
        }
        return true;
    }

    /** 查找群成员 */
    @OnEvent(value = "groupMember")
    public void onGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.groupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // 是否是群组成员
                GroupVO groupVO = new GroupVO();
                groupVO.setGroupsId(groupMemberVO.getGroupId());
                groupVO.setMemberId(memberVO.getMemberId());
                if (iGroupMemberService.isGroupMember(groupVO)){
                    groupMemberVO.setMemberId(memberVO.getMemberId());
                    client.sendEvent("groupMemberResult", ResultUtil.success(iGroupMemberService.getGroupMember(groupMemberVO)));
                }
            }
        } else {
            client.sendEvent("groupMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 获取添加群组成员 */
    @OnEvent(value = "getAddGroupMember")
    public void onGetAddGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.getAddGroupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // 是否是群组管理员，否则不能获取添加群组成员
                GroupVO groupVO = new GroupVO();
                groupVO.setGroupsId(groupMemberVO.getGroupId());
                groupVO.setMemberId(memberVO.getMemberId());
                if (iGroupService.isGroupAdministrator(groupVO)) {
                    groupMemberVO.setMemberId(memberVO.getMemberId());
                    client.sendEvent("getAddGroupMemberResult", ResultUtil.success(iGroupMemberService.getAddGroupMember(groupMemberVO)));
                }
            }
        } else {
            client.sendEvent("getAddGroupMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 获取移除群组成员 */
    @OnEvent(value = "getRemoveGroupMember")
    public void onGetRemoveGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.getRemoveGroupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // 是否是群组管理员，否则不能获取移除群组成员
                GroupVO groupVO = new GroupVO();
                groupVO.setGroupsId(groupMemberVO.getGroupId());
                groupVO.setMemberId(memberVO.getMemberId());
                if (iGroupService.isGroupAdministrator(groupVO)) {
                    groupMemberVO.setMemberId(memberVO.getMemberId());
                    client.sendEvent("getRemoveGroupMemberResult", ResultUtil.success(iGroupMemberService.getRemoveGroupMember(groupMemberVO)));
                }
            }
        } else {
            client.sendEvent("getRemoveGroupMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 添加成员 */
    @OnEvent(value = "addGroupMember")
    public void onAddGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.addGroupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                if (checkGroupMemberIdsByAdd(client, groupMemberVO.getMemberIds(), memberVO.getMemberId(), groupMemberVO)) {
                    // 是否是群组管理员，否则不能添加成员
                    GroupVO groupVO = new GroupVO();
                    groupVO.setGroupsId(groupMemberVO.getGroupId());
                    groupVO.setMemberId(memberVO.getMemberId());
                    if (iGroupService.isGroupAdministrator(groupVO)){
                        client.sendEvent("addGroupMemberResult", ResultUtil.success(iGroupMemberService.addGroupMember(groupMemberVO)));
                    }
                }
            }
        } else {
            client.sendEvent("addGroupMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 检测群组用户id数组群组添加成员 */
    private boolean checkGroupMemberIdsByAdd(SocketIOClient client, Integer[] memberIds, Integer memberId, GroupMemberVO groupMemberVO) {
        if (!checkGroupMemberIds(client, memberIds, memberId, "addGroupMemberResult")){
            return false;
        }
        if (iGroupMemberService.getGroupCount(groupMemberVO)+memberIds.length > iBaseConfigService.getBaseConfig().getMaxGroupMember()){
            client.sendEvent("addGroupMemberResult", ResultUtil.error(0, "人数已达上限，无法邀请加入"));
            return false;
        }
        return true;
    }

    /** 移除成员 */
    @OnEvent(value = "removeGroupMember")
    public void onRemoveGroupMemeber(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.removeGroupMemeber(client, groupMemberVO)) {
                // 是否是群组管理员，否则不能移除成员
                GroupVO groupVO = new GroupVO();
                groupVO.setGroupsId(groupMemberVO.getGroupId());
                groupVO.setMemberId(clientSession.get().getMemberId());
                if (iGroupService.isGroupAdministrator(groupVO)) {
                    client.sendEvent("removeGroupMemberResult", ResultUtil.success(iGroupMemberService.removeGroupMemeber(groupMemberVO)));
                }
            }
        } else {
            client.sendEvent("removeGroupMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 退出群组 */
    @OnEvent(value = "quitGroup")
    public void onQuitGroup(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.quitGroup(client, groupMemberVO)) {
                groupMemberVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("quitGroupResult", ResultUtil.success(iGroupMemberService.quitGroup(groupMemberVO)));
            }
        } else {
            client.sendEvent("quitGroupResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 解散群组 */
    @OnEvent(value = "disbandGroup")
    public void onDisbandGroup(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.disbandGroup(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // 是否是群组管理员，否则不能解散群组
                GroupVO groupVO = new GroupVO();
                groupVO.setGroupsId(groupMemberVO.getGroupId());
                groupVO.setMemberId(memberVO.getMemberId());
                if (iGroupService.isGroupAdministrator(groupVO)){
                    groupMemberVO.setMemberId(memberVO.getMemberId());
                    client.sendEvent("disbandGroupResult", ResultUtil.success(iGroupMemberService.disbandGroup(groupMemberVO)));
                }
            }
        } else {
            client.sendEvent("disbandGroupResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 编辑群组 */
    @OnEvent(value = "editGroup")
    public void onEeditGroup(SocketIOClient client, AckRequest ackRequest, GroupVO groupVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.editGroup(client, groupVO)) {
                groupVO.setMemberId(clientSession.get().getMemberId());
                // 是否是群组管理员，否则不能修改群组信息
                if (iGroupService.isGroupAdministrator(groupVO)){
                    client.sendEvent("editGroupResult", ResultUtil.success(iGroupService.editGroup(groupVO)));
                }
            }
        } else {
            client.sendEvent("editGroupResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** 群组列表 */
    @OnEvent(value = "groupList")
    public void onGroupList(SocketIOClient client, GroupVO groupVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // 判断前端对参数误传为空
            if (groupVO == null) {
                groupVO = new GroupVO();
            }
            groupVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("groupListResult", ResultUtil.success(iGroupMemberService.listGroup(groupVO)));
        } else {
            client.sendEvent("groupListResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

}
