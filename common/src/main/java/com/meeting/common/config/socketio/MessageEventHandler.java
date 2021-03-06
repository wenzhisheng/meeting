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
 * @description socketio?????????
 * @dateTime 2019-06-14 14:41
 * @className com.meeting.common.config.socketio.MessageEventHandler
 */
@Component
public class MessageEventHandler {

    /** Log4j?????? */
    private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);
    /** ConcurrentHashMap????????????????????????HashMap????????????????????????SU(SessionidUser) */
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

    /** ????????????????????????????????? */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if(client != null) {
            String sessionId = client.getSessionId().toString();
            logger.info("????????????,clientSessionId={}", sessionId);
        } else {
            logger.error("????????????");
        }
    }

    /** ?????????????????????????????????????????????????????? */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        // ????????????????????????????????????
        String account = CommonConst.STRING_NULL;
        if (mapSU != null && mapSU.size() != 0){
            String sessionId = client.getSessionId().toString();
            account = mapSU.get(sessionId);
            mapSU.remove(sessionId);
            // ???????????????????????????????????????????????????sessionid??????????????????????????????????????????????????????
            String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, account);
            RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
            clientMap.put(RedisKeyConst.MEETING_ONLINE_STATUS, CommonConst.OFFLINE_ONLINE);
            clientMap.expire(15, TimeUnit.DAYS);
            // ??????????????????????????????????????????ID
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if(clientSession != null){
                clientSession.delete();
            }
        }
        client.disconnect();
        logger.info("{}??????????????????,clientSessionId={}", account, client.getSessionId().toString());
    }

    /** ???????????? */
    @OnEvent(value = "historyMessage")
    public void onHistoryMessage(SocketIOClient client, AckRequest ackRequest, HistoryMessageVO message) {
        // ?????????????????????????????????
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

    /** ??????30???????????? */
    @OnEvent(value = "listMessage")
    public void onMessageList(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // ?????????????????????????????????
        if (!listMessage(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                // ??????30???????????????
                chatMessageVO.setMemberId(clientSession.get().getMemberId());
                List<MessageResultDTO> historyMessage = iFriendMessageService.listMessage(chatMessageVO);
                // ??????????????????
                client.sendEvent("listMessageResult", ResultUtil.success(historyMessage));
            } else {
                client.sendEvent("listMessageResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** ??????????????????(????????????????????????????????????????????????) */
    @OnEvent(value = "dialogMoreInfo")
    public void onDialogMoreInfo(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // ?????????????????????????????????
        if (!listMessage(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                chatMessageVO.setMemberId(clientSession.get().getMemberId());
                // ??????????????????
                client.sendEvent("dialogMoreInfoResult", ResultUtil.success(iMemberService.getMemberLoginTime(chatMessageVO)));
            } else {
                client.sendEvent("dialogMoreInfoResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** ???????????? */
    @OnEvent(value = "sendMsg")
    public void onSendMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // ????????????
        if (!sendMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                RLock fairLock = null;
                try{
                    // ???????????????????????????????????????
                    fairLock = getrLock(client, memberVO, fairLock);
                    // ????????????(single:?????????cluster:?????????system:??????)
                    String messageTyle = chatMessageVO.getMessageType();
                    chatMessageVO.setMemberId(memberVO.getMemberId());
                    switch (messageTyle) {
                        case CommonConst.MESSAGE_CLASS_SINGLE:
                            // ????????????
                            chatMessageVO.setMemberAccount(memberVO.getAccount());
                            chatMessageVO.setMemberAlias(memberVO.getNickname());
                            chatMessageVO.setMemberAvatar(memberVO.getAvatar());
                            singleSendMsg(client, chatMessageVO, memberVO);
                            break;
                        case CommonConst.MESSAGE_CLASS_CLUSTER:
                            // ????????????
                            clusterSendMsg(client, chatMessageVO, memberVO);
                            break;
                        case CommonConst.MESSAGE_CLASS_SYSTEM:
                            // ????????????
                            break;
                        default:
                            client.sendEvent("listMessageResult", ResultUtil.error(0, "??????????????????"));
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

    /** redisson??? */
    private RLock getrLock(SocketIOClient client, MemberVO memberVO, RLock fairLock) {
        String friendFairLock = MessageFormat.format("{0}{1}", client.getSessionId(), memberVO.getAccount());
        fairLock = redissonClient.getFairLock(friendFairLock);
        fairLock.lock();
        return fairLock;
    }

    /** ?????????????????? */
    private void clusterSendMsg(SocketIOClient client, ChatMessageVO chatMessageVO, MemberVO memberVO) {
        chatMessageVO.setAccount(chatMessageVO.getAccount());
        // ????????????????????????
        chatMessageVO.setMsgid(CommonUtil.generateOrderNo(memberVO.getMemberId()));
        // ????????????
        saveGroupMessage(chatMessageVO, memberVO);
        // ????????????
        iDialogService.save(chatMessageVO);
        // ??????30????????????????????????????????????
        List<MessageResultDTO> listMessage = iGroupMessageService.listHistoryMessage(chatMessageVO);
        // ????????????????????????
        server.getRoomOperations(chatMessageVO.getAccount()).sendEvent("listMessageResult", listMessage, new BroadcastAckCallback<String>(String.class) {
            @Override
            protected void onClientTimeout(SocketIOClient client) {
                logger.info("{}??????????????????{}", chatMessageVO.getAlias() , "?????????");
            }
            @Override
            protected void onClientSuccess(SocketIOClient client, String result) {
                logger.info("{}??????????????????{}", chatMessageVO.getAlias(), "?????????");
            }
        });
        // ????????????
        singleAndClusterSend(client, memberVO, listMessage);
        logger.info("??????{}?????????{}????????????:{}", memberVO.getAccount(), chatMessageVO.getAccount(), chatMessageVO.getContent());
    }

    private List<DialogVO> singleAndClusterSend(SocketIOClient client, MemberVO memberVO, List<MessageResultDTO> listMessage) {
        // ????????????
        DialogVO dialogVO = new DialogVO();
        dialogVO.setMemberId(memberVO.getMemberId());
        List<DialogVO> listDialog = iDialogService.listDialog(dialogVO);
        // ?????????????????????????????????
        client.sendEvent("listMessageResult", ResultUtil.success(listMessage));
        client.sendEvent("dialogueListResult", ResultUtil.success(listDialog));
        return listDialog;
    }

    private void singleSendMsg(SocketIOClient client, ChatMessageVO chatMessageVO, MemberVO memberVO) {
        // ????????????????????????
        chatMessageVO.setMsgid(CommonUtil.generateOrderNo(memberVO.getMemberId()));
        // ??????????????????
        saveFriendMessage(chatMessageVO, memberVO);
        // ????????????
        iDialogService.save(chatMessageVO);
        // ??????????????????
        List<MessageResultDTO> listMessage = iFriendMessageService.listMessage(chatMessageVO);
        // ????????????
        List<DialogVO> listDialog = singleAndClusterSend(client, memberVO, listMessage);
        // ????????????
        sendMessageList(chatMessageVO, listMessage, listDialog);
        logger.info("??????{}????????????{}????????????:{}", memberVO.getAccount(), chatMessageVO.getAccount(), chatMessageVO.getContent());
    }

    /** ???????????? */
    @OnEvent(value = "readMsg")
    public void onReadMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        logger.info("????????????{}????????????", chatMessageVO.getTargetId());
        // ????????????
        if (!readMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                String messageTyle = chatMessageVO.getMessageType();
                // ????????????(single:?????????cluster:?????????system:??????)
                chatMessageVO.setMemberId(memberVO.getMemberId());
                switch (messageTyle) {
                    case CommonConst.MESSAGE_CLASS_SINGLE:
                        // ??????????????????
                        singleAndClusterReadMsg(client, chatMessageVO, memberVO);
                        logger.info("??????{}????????????{}????????????", memberVO.getAccount(), chatMessageVO.getTargetId());
                        break;
                    case CommonConst.MESSAGE_CLASS_CLUSTER:
                        // ??????????????????
                        singleAndClusterReadMsg(client, chatMessageVO, memberVO);
                        logger.info("??????{}?????????{}????????????", memberVO.getAccount(), chatMessageVO.getTargetId());
                        break;
                    default:
                        client.sendEvent("listMessageResult", ResultUtil.error(0, "??????????????????"));
                        break;
                }
            }
        }
    }

    private void singleAndClusterReadMsg(SocketIOClient client, ChatMessageVO chatMessageVO, MemberVO memberVO) {
        // ????????????
        iDialogService.updateUnread(chatMessageVO);
        // ????????????????????????
        DialogVO dialogVO = new DialogVO();
        dialogVO.setMemberId(memberVO.getMemberId());
        client.sendEvent("dialogueListResult", ResultUtil.success(iDialogService.listDialog(dialogVO)));
    }

    /** ???????????? */
    @OnEvent(value = "recallMsg")
    public void onRecallMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // ????????????????????????
        if (!deleteMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                // ????????????(single:?????????cluster:?????????system:??????)
                String messageTyle = chatMessageVO.getMessageType();
                chatMessageVO.setMemberId(memberVO.getMemberId());
                switch (messageTyle) {
                    case CommonConst.MESSAGE_CLASS_SINGLE:
                        // ??????????????????
                        recallFriendMessage(chatMessageVO);
                        // ???????????????
                        snedAndCallback(client, chatMessageVO);
                        logger.info("??????{}????????????{}????????????:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    case CommonConst.MESSAGE_CLASS_CLUSTER:
                        // ??????????????????
                        recallGroupMessage(chatMessageVO);
                        // ???????????????
                        snedAndCallback(client, chatMessageVO);
                        logger.info("??????{}?????????{}????????????:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    default:
                        client.sendEvent("listMessageResult", ResultUtil.error(0, "??????????????????"));
                        break;
                }
            }
        }
    }

    private void snedAndCallback(SocketIOClient client, ChatMessageVO chatMessageVO) {
        // ????????????
        DialogVO dialogVO = new DialogVO();
        dialogVO.setMemberId(chatMessageVO.getMemberId());
        List<DialogVO> listDialog = iDialogService.listDialog(dialogVO);
        // ??????????????????
        List<MessageResultDTO> listMessage = iFriendMessageService.listMessage(chatMessageVO);
        // ?????????????????????????????????
        client.sendEvent("listMessageResult", ResultUtil.success(listMessage));
        client.sendEvent("dialogueListResult", ResultUtil.success(listDialog));
        // ??????????????????
        dialogVO.setMemberId(chatMessageVO.getMemberId());
        sendMessageList(chatMessageVO, listMessage, iDialogService.listDialog(dialogVO));
    }

    /** ???????????? */
    @OnEvent(value = "deleteMsg")
    public void onDeleteMsg(SocketIOClient client, AckRequest ackRequest, ChatMessageVO chatMessageVO) {
        // ????????????????????????
        if (!deleteMsg(client, chatMessageVO)){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                MemberVO memberVO = clientSession.get();
                // ????????????(single:?????????cluster:?????????system:??????)
                String messageTyle = chatMessageVO.getMessageType();
                chatMessageVO.setMemberId(memberVO.getMemberId());
                switch (messageTyle) {
                    case CommonConst.MESSAGE_CLASS_SINGLE:
                        // ??????????????????
                        deleteFriendMessage(chatMessageVO);
                        // ???????????????
                        snedAndCallback(client, chatMessageVO);
                        logger.info("??????{}?????????{}????????????:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    case CommonConst.MESSAGE_CLASS_CLUSTER:
                        // ??????????????????
                        deleteGroupMessage(chatMessageVO);
                        // ???????????????
                        snedAndCallback(client, chatMessageVO);
                        logger.info("??????{}?????????{}????????????:{}", memberVO.getAccount(), chatMessageVO.getTargetId(), chatMessageVO.getContent());
                        break;
                    default:
                        client.sendEvent("listMessageResult", ResultUtil.error(0, "??????????????????"));
                        break;
                }
            }
        }
    }

    /** ?????????????????? */
    private void sendMessageList(ChatMessageVO chatMessageVO, List<MessageResultDTO> listMessage, List<DialogVO> listDialog) {
        // ????????????????????????
        String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, chatMessageVO.getAccount());
        RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
        if (clientMap != null && clientMap.size() != 0) {
            // ???????????????????????????redis,?????????????????????
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

    /** ?????????????????? */
    private void readMessageList(String account, List<DialogVO> listDialog) {
        // ????????????????????????
        String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, account);
        RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
        if (clientMap != null && clientMap.size() != 0) {
            // ?????????????????????????????????????????????????????????????????????
            if (CommonConst.ONLINE_OFFLINE.equals(clientMap.get(RedisKeyConst.MEETING_ONLINE_STATUS))) {
                UUID sessionid = (UUID) clientMap.get(RedisKeyConst.MEETING_SOCKETIO_UUID);
                server.getClient(sessionid).sendEvent("dialogueListResult", ResultUtil.success(listDialog));
            }
        }
    }

    /** ?????????????????? */
    private void recallFriendMessage(ChatMessageVO chatMessageVO) {
        FriendMessageVO friendMessageVO = new FriendMessageVO();
        friendMessageVO.setIsBack(1);
        friendMessageVO.setMsgid(chatMessageVO.getMsgid());
        iFriendMessageService.readOrRecallOrDelete(friendMessageVO);
    }

    /** ?????????????????? */
    private void recallGroupMessage(ChatMessageVO chatMessageVO) {
        GroupMessageVO groupMessageVO = new GroupMessageVO();
        groupMessageVO.setIsBack(1);
        groupMessageVO.setMsgid(chatMessageVO.getMsgid());
        iGroupMessageService.readOrRecallOrDelete(groupMessageVO);
    }

    /** ?????????????????? */
    private void deleteFriendMessage(ChatMessageVO chatMessageVO) {
        FriendMessageVO friendMessageVO = new FriendMessageVO();
        friendMessageVO.setIsDel(1);
        friendMessageVO.setMsgid(chatMessageVO.getMsgid());
        iFriendMessageService.readOrRecallOrDelete(friendMessageVO);
    }

    /** ?????????????????? */
    private void deleteGroupMessage(ChatMessageVO chatMessageVO) {
        GroupMessageVO groupMessageVO = new GroupMessageVO();
        groupMessageVO.setIsDel(1);
        groupMessageVO.setMsgid(chatMessageVO.getMsgid());
        iGroupMessageService.readOrRecallOrDelete(groupMessageVO);
    }

    /** ???????????? */
    private void saveFriendMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        FriendMessageVO friendMessageVO = saveInitFriendMessage(chatMessageVO, memberVO);
        friendMessageVO.setMessageType("text");
        iFriendMessageService.save(friendMessageVO);
    }

    /** ???????????? */
    private void saveGroupMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        GroupMessageVO groupMessageVO = saveInitGroupMessage(chatMessageVO, memberVO);
        groupMessageVO.setMessageType("text");
        iGroupMessageService.save(groupMessageVO);
    }

    /** ?????? */
    @OnEvent(value = "login")
    public void onLogin(SocketIOClient client, AckRequest ackRequest, LoginRequestVO loginRequestVO) {
        //if(ackRequest.isAckRequested()) {
            //ackRequest.sendAckData("login success");
        //}
        // DTO??????VO
        MemberVO memberVO = POJOConvertUtil.convertPojo(loginRequestVO, MemberVO.class);
        if (StringUtils.isEmpty(memberVO.getAccount()) && StringUtils.isEmpty(memberVO.getPassword())){
            client.sendEvent("loginResult", ResultUtil.error(0,"????????????????????????"));
        }
        // ??????????????????
        String sessionId = client.getSessionId().toString();
        MemberVO memberVO1 = iMemberService.login(memberVO);
        if (memberVO1 != null){
            memberVO1.setSessionId(sessionId);
            // ???????????????????????????
            String account = memberVO1.getAccount();
            mapSU.put(sessionId, account);
            // ??????????????????
            cacheLoginInfo(client, memberVO1);
            // ???????????????????????????????????????????????????
            client.sendEvent("loginResult", ResultUtil.success(memberVO1));
            // ??????????????????
            iMemberService.updateLoginTime(memberVO1);
            // ??????????????????
            sendOfflineMessage(client, memberVO1);
            // ?????????????????????????????????????????????????????????
            List<GroupVO> groupList = iGroupService.list(memberVO1);
            for (GroupVO groupVO : groupList) {
                client.joinRoom(groupVO.getAccount());
            }
            logger.info("{}??????????????????,clientSessionId={}", loginRequestVO.getAccount(), client.getSessionId());
        }else{
            client.sendEvent("loginResult", ResultUtil.error(0,"?????????????????????"));
        }
    }

    /** ?????????????????? */
    private void cacheLoginInfo(SocketIOClient client, MemberVO memberVO1) {
        String socketioClientKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_TARGET, memberVO1.getAccount());
        RMap<String, Object> clientMap = redissonClient.getMap(socketioClientKey);
        // ??????????????????
        if(clientMap != null || CommonConst.ONLINE_OFFLINE.equals(clientMap.get(RedisKeyConst.MEETING_ONLINE_STATUS))){
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, clientMap.get(RedisKeyConst.MEETING_SOCKETIO_UUID));
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            clientSession.delete();
        }
        // ??????????????????????????????uuid??????????????????????????????????????????????????????
        clientMap.put(RedisKeyConst.MEETING_SOCKETIO_UUID, client.getSessionId());
        clientMap.put(RedisKeyConst.MEETING_ONLINE_STATUS, CommonConst.ONLINE_OFFLINE);
        // ????????????????????????????????????ID
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        clientSession.set(memberVO1);
    }

    /** ?????????????????? */
    private void sendOfflineMessage(SocketIOClient client, MemberVO memberVO1) {
        // ?????????????????????,?????????????????????
        String offlineMessageKey = MessageFormat.format(RedisKeyConst.MEETING_OFFLINE_MESSAGE, memberVO1.getAccount());
        RList<ChatMessageVO> messageList = redissonClient.getList(offlineMessageKey);
        if (messageList != null && messageList.size() != 0){
            for(ChatMessageVO chatMessageVO: messageList){
                // ????????????30????????????????????????????????????
                chatMessageVO.setMemberId(memberVO1.getMemberId());
                client.sendEvent("listMessageMsg", ResultUtil.success(iFriendMessageService.listMessage(chatMessageVO)));
            }
            // ???????????????????????????
            messageList.delete();
        }
    }

    /** ??????????????? */
    @OnEvent(value = "menus")
    public void onMenus(SocketIOClient client, AckRequest ackRequest) {
        client.sendEvent("menusList", ResultUtil.success(iNavigationService.list(new NavigationVO())));
    }

    /** ????????? */
    @OnEvent(value = "areaProvince")
    public void onAreaProvince(SocketIOClient client, AckRequest ackRequest) {
        client.sendEvent("areaProvinceResult", ResultUtil.success(iAreaCascadeService.provinceList()));
    }

    /** ????????? */
    @OnEvent(value = "areaCityCounty")
    public void onAreaCityCounty(SocketIOClient client, AckRequest ackRequest, AreaCascadeVO areaCascadeVO) {
        if (!CheckParameter.areaCityCounty(client, areaCascadeVO)) {
            client.sendEvent("areaCityCountyResult", ResultUtil.success(iAreaCascadeService.cityList(areaCascadeVO)));
        }
    }

    /** ????????? */
    @OnEvent(value = "areaCounty")
    public void onAreaCounty(SocketIOClient client, AckRequest ackRequest, AreaCascadeVO areaCascadeVO) {
        if (!CheckParameter.areaCounty(client, areaCascadeVO)) {
            client.sendEvent("areaCountyResult", ResultUtil.success(iAreaCascadeService.cityList(areaCascadeVO)));
        }
    }

    /** ???????????? */
    @OnEvent(value = "areaCascade")
    public void onAreaCascade(SocketIOClient client, AckRequest ackRequest) {
        client.sendEvent("areaCascadeResult", ResultUtil.success(iAreaCascadeService.cascadeList()));
    }

    /** ???????????? */
    @OnEvent(value = "application")
    public void onApplication(SocketIOClient client, AckRequest ackRequest) {
        logger.info("333333333333333333333333333333333333");
        client.sendEvent("applicationList", ResultUtil.success(iApplicationService.list(new ApplicationVO())));
    }

    /** ???????????? */
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

    /** ??????????????? */
    @OnEvent(value = "searchToAdd")
    public void onSearchToAdd(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            friendVO.setMemberId(clientSession.get().getMemberId());
            MemberVO memberVO = iMemberService.searchByAccount(friendVO);
            if (memberVO != null){
                friendVO.setTargetId(memberVO.getMemberId());
                // ?????????????????????????????????
                if (iFriendService.isDeleteFriend(friendVO)){
                    memberVO.setIsFriend("yes");
                }else if(memberVO.getMemberId().equals(clientSession.get().getMemberId())){
                    memberVO.setIsFriend("oneself");
                } else {
                    memberVO.setIsFriend("no");
                }
                client.sendEvent("searchToAddResult", ResultUtil.success(memberVO));
            }else{
                client.sendEvent("searchToAddResult", ResultUtil.success("???????????????"));
            }
        } else {
            client.sendEvent("searchToAddResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ???????????? */
    @OnEvent(value = "addFriend")
    public void onAddFriend(SocketIOClient client, AckRequest ackRequest, FriendApplyVO friendApplyVO) {
        // ?????????????????????????????????
        if (!CheckParameter.addFriend(client, friendApplyVO)) {
            if (StringUtils.isEmpty(friendApplyVO.getRemark())){
                friendApplyVO.setRemark("????????????????????????");
            }
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                // ?????????????????????????????????
                friendApplyVO.setMemberId(friendApplyVO.getTargetId());
                friendApplyVO.setTargetId(clientSession.get().getMemberId());
                // ??????????????????
                int maxFriend = iBaseConfigService.getBaseConfig().getMaxFriend();
                int countFriendMeember = iFriendService.countFriendMeember(friendApplyVO);
                int countFriendTarget = iFriendService.countFriendTarget(friendApplyVO);
                // ??????????????????????????????????????????????????????????????????
                if (countFriendMeember < maxFriend && countFriendTarget < maxFriend){
                    client.sendEvent("addFriendResult", ResultUtil.success(iMemberService.addFriend(friendApplyVO)));
                } else {
                    client.sendEvent("addFriendResult", ResultUtil.error(0, "??????????????????"));
                }
            } else {
                client.sendEvent("addFriendResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** ???????????? */
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

    /** ???????????? */
    @OnEvent(value = "agreeAdd")
    public void onAgreeAdd(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        // ?????????????????????????????????
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

    /** ???????????? */
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

    /** ???????????? */
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

    /** ???????????? */
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
            //?????????????????????????????????
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            buf = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
            //login();
            isConnected = true;
        }catch (IOException ex){
            logger.error("11{}",ex);
        }
    }*/

    /** ???????????? */
    @OnEvent(value = "editAlias")
    public void onEditAlias(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        if (friendVO.getRemark().length() > 16){
            client.sendEvent("editAliasResult", ResultUtil.error(0, "??????????????????"));
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

    /** ???????????? */
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

    /** ???????????? */
    @OnEvent(value = "newMember")
    public void onNnewMember(SocketIOClient client, AckRequest ackRequest) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // ???????????????,?????????????????????????????????
            MemberVO memberVO = iMemberService.getMemberById(clientSession.get());
            clientSession.delete();
            clientSession.set(memberVO);
            client.sendEvent("newMemberResult", ResultUtil.success(memberVO));
        } else {
            client.sendEvent("newMemberResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ??????????????? */
    @OnEvent(value = "searchLinkman")
    public void onSearchMember(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            friendVO.setMemberId(clientSession.get().getMemberId());
            // list?????????????????????class???????????????????????????????????????????????????????????????
            if (CommonConst.SEARCH_LIST.equals(friendVO.getSearchType())){
                client.sendEvent("searchLinkmanResult", ResultUtil.success(iFriendService.listFriend(friendVO)));
            }else if (CommonConst.SEARCH_CLASS.equals(friendVO.getSearchType())){
                client.sendEvent("searchLinkmanResult", ResultUtil.success(iFriendService.listClass(friendVO)));
            }
        } else {
            client.sendEvent("searchLinkmanResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ??????????????? */
    @OnEvent(value = "blacklistList")
    public void onBlacklistList(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // ??????????????????????????????
            if (friendVO == null) {
                friendVO = new FriendVO();
            }
            friendVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("blacklistListResult", ResultUtil.success(iFriendService.blacklistList(friendVO)));
        } else {
            client.sendEvent("blacklistListResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ??????????????? */
    @OnEvent(value = "linkmanTiled")
    public void onLinkmanTiled(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // ?????????????????????????????????
            if (friendVO == null) {
                friendVO = new FriendVO();
            }
            friendVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("linkmanTiledList", ResultUtil.success(iFriendService.listFriend(friendVO)));
        }else{
            client.sendEvent("linkmanTiledList", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ??????????????? */
    @OnEvent(value = "linkmanClassify")
    public void onLinkmanClassify(SocketIOClient client, AckRequest ackRequest, FriendVO friendVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // ?????????????????????????????????
            if (friendVO == null) {
                friendVO = new FriendVO();
            }
            friendVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("linkmanClassifyClass", ResultUtil.success(iFriendService.listClass(friendVO)));
        }else{
            client.sendEvent("linkmanClassifyClass", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ???????????? */
    @OnEvent(value = "dialogueList")
    public void onDialogueList(SocketIOClient client, AckRequest ackRequest, DialogVO dialogVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (dialogVO == null){
                dialogVO = new DialogVO();
            }
            // ??????????????????
            dialogVO.setMemberId(clientSession.get().getMemberId());
            client.sendEvent("dialogueListResult", ResultUtil.success(iDialogService.listDialog(dialogVO)));
        } else {
            client.sendEvent("dialogueListResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ???????????? */
    @OnEvent(value = "deleteDialogue")
    public void onDeleteDialogue(SocketIOClient client, AckRequest ackRequest, DialogVO dialogVO) {
        if (!CheckParameter.deleteDialogue(client, dialogVO)) {
            String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
            RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
            if (clientSession != null && clientSession.get() != null){
                // ?????????????????????????????????????????????????????????
                dialogVO.setMemberId(clientSession.get().getMemberId());
                client.sendEvent("deleteDialogueResult", ResultUtil.success(iDialogService.deleteDialogue(dialogVO)));
                client.sendEvent("dialogueListResult", ResultUtil.success(iDialogService.listDialog(dialogVO)));
                // ??????????????????
                dialogVO.setMemberId(dialogVO.getTargetId());
                readMessageList(dialogVO.getAccount(), iDialogService.listDialog(dialogVO));
            } else {
                client.sendEvent("deleteDialogueResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
            }
        }
    }

    /** ???????????? */
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

    /** ???????????? */
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

    /** ???????????? */
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
                    // ??????????????????
                    BaseConfigVO baseConfig = iBaseConfigService.getBaseConfig();
                    int maxFriend = baseConfig.getMaxGroup();
                    int countGroup = iGroupService.countGroup(groupVO);
                    // ??????????????????????????????????????????????????????????????????????????????
                    if (countGroup < maxFriend && groupVO.getMemberIds().length+1 <= baseConfig.getMaxGroupMember()){
                        client.sendEvent("buildGroupResult", ResultUtil.success(iGroupService.insert(groupVO)));
                    } else {
                        client.sendEvent("buildGroupResult", ResultUtil.error(0, "???????????????????????????"));
                    }
                }
            }
        } else {
            client.sendEvent("buildGroupResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ??????????????????id?????? */
    private boolean checkGroupMemberIds(SocketIOClient client, Integer[] memberIds, Integer memberId, String result) {
        FriendVO friendVO = new FriendVO();
        friendVO.setMemberId(memberId);
        for (int i=0; i<memberIds.length; i++){
            friendVO.setTargetId(memberIds[i]);
            if (!iFriendService.isFriend(friendVO)){
                client.sendEvent(result, ResultUtil.error(0,"?????????ID??????"));
                return false;
            }
        }
        return true;
    }

    /** ??????????????? */
    @OnEvent(value = "groupMember")
    public void onGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.groupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // ?????????????????????
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

    /** ???????????????????????? */
    @OnEvent(value = "getAddGroupMember")
    public void onGetAddGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.getAddGroupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // ???????????????????????????????????????????????????????????????
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

    /** ???????????????????????? */
    @OnEvent(value = "getRemoveGroupMember")
    public void onGetRemoveGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.getRemoveGroupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // ???????????????????????????????????????????????????????????????
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

    /** ???????????? */
    @OnEvent(value = "addGroupMember")
    public void onAddGroupMember(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.addGroupMember(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                if (checkGroupMemberIdsByAdd(client, groupMemberVO.getMemberIds(), memberVO.getMemberId(), groupMemberVO)) {
                    // ???????????????????????????????????????????????????
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

    /** ??????????????????id???????????????????????? */
    private boolean checkGroupMemberIdsByAdd(SocketIOClient client, Integer[] memberIds, Integer memberId, GroupMemberVO groupMemberVO) {
        if (!checkGroupMemberIds(client, memberIds, memberId, "addGroupMemberResult")){
            return false;
        }
        if (iGroupMemberService.getGroupCount(groupMemberVO)+memberIds.length > iBaseConfigService.getBaseConfig().getMaxGroupMember()){
            client.sendEvent("addGroupMemberResult", ResultUtil.error(0, "???????????????????????????????????????"));
            return false;
        }
        return true;
    }

    /** ???????????? */
    @OnEvent(value = "removeGroupMember")
    public void onRemoveGroupMemeber(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.removeGroupMemeber(client, groupMemberVO)) {
                // ???????????????????????????????????????????????????
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

    /** ???????????? */
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

    /** ???????????? */
    @OnEvent(value = "disbandGroup")
    public void onDisbandGroup(SocketIOClient client, AckRequest ackRequest, GroupMemberVO groupMemberVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.disbandGroup(client, groupMemberVO)) {
                MemberVO memberVO = clientSession.get();
                // ???????????????????????????????????????????????????
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

    /** ???????????? */
    @OnEvent(value = "editGroup")
    public void onEeditGroup(SocketIOClient client, AckRequest ackRequest, GroupVO groupVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            if (!CheckParameter.editGroup(client, groupVO)) {
                groupVO.setMemberId(clientSession.get().getMemberId());
                // ?????????????????????????????????????????????????????????
                if (iGroupService.isGroupAdministrator(groupVO)){
                    client.sendEvent("editGroupResult", ResultUtil.success(iGroupService.editGroup(groupVO)));
                }
            }
        } else {
            client.sendEvent("editGroupResult", ResultUtil.error(-1, CommonConst.ILLEGAL_OPERATE));
        }
    }

    /** ???????????? */
    @OnEvent(value = "groupList")
    public void onGroupList(SocketIOClient client, GroupVO groupVO) {
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, client.getSessionId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            // ?????????????????????????????????
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
