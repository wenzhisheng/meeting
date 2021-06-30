package com.meeting.common.friendmessage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.friendmessage.dao.IFriendMessageDao;
import com.meeting.common.friendmessage.service.IFriendMessageService;
import com.meeting.common.groupmessage.dao.IGroupMessageDao;
import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import com.meeting.common.pojo.friendmessage.MessageResultDTO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.pojo.socketio.HistoryMessage2VO;
import com.meeting.common.pojo.socketio.HistoryMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dameizi
 * @description 单聊消息
 * @dateTime 2019-04-25 17:40
 * @className com.meeting.meeting.friendmessage.service.impl.FriendMessageServiceImpl
 */
@Service
public class FriendMessageServiceImpl implements IFriendMessageService {

    @Autowired
    private IFriendMessageDao iFriendMessageDao;
    @Autowired
    private IGroupMessageDao iGroupMessageDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-20 10:27
     * @description: 获取历史消息，触发获取，第二页开始
     * @param: [chatMessageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    @Override
    public IPage<MessageResultDTO> getHistoryMessage2(HistoryMessage2VO chatMessageVO) {
        Page<MessageResultDTO> page = new Page<>(chatMessageVO.getPageNo(), chatMessageVO.getPageSize());
        return iFriendMessageDao.getHistoryMessage2(page, chatMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-20 10:27
     * @description: 分页查询
     * @param: [chatMessageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    @Override
    public IPage<MessageResultDTO> getHistoryMessage(HistoryMessageVO chatMessageVO) {
        Page<MessageResultDTO> page = new Page<>(chatMessageVO.getPageNo(), chatMessageVO.getPageSize());
        return iFriendMessageDao.getHistoryMessage(page, chatMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 11:59
     * @description: 最新30条消息
     * @param: [chatMessageVO]
     * @return: java.util.List<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    @Override
    public List<MessageResultDTO> listMessage(ChatMessageVO chatMessageVO) {
        // 判断是单聊还是群聊，给出不同数据
        if (CommonConst.DIALOG_TYPE_SINGLE.equals(chatMessageVO.getMessageType())){
            List<MessageResultDTO> listMessage = iFriendMessageDao.listMessage(chatMessageVO);
            if (listMessage != null) {
                return listMessage;
            }
        } else if (CommonConst.DIALOG_TYPE_CLUSTER.equals(chatMessageVO.getMessageType())){
            List<MessageResultDTO> listMessage = iGroupMessageDao.listMessage(chatMessageVO);
            if (listMessage != null) {
                return listMessage;
            }
        }
        return new ArrayList<>();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 21:24
     * @description: 撤回或删除
     * @param: [friendMessageVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int readOrRecallOrDelete(FriendMessageVO friendMessageVO) {
        return iFriendMessageDao.readOrRecallOrDelete(friendMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 15:54
     * @description: 保存聊天记录
     * @param: [friendMessageVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(FriendMessageVO friendMessageVO) {
        return iFriendMessageDao.save(friendMessageVO);
    }

}
