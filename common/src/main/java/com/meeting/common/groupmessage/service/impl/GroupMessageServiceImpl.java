package com.meeting.common.groupmessage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.groupmessage.dao.IGroupMessageDao;
import com.meeting.common.groupmessage.service.IGroupMessageService;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.friendmessage.MessageResultDTO;
import com.meeting.common.pojo.groupmessage.GroupMessageVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dameizi
 * @description 群聊消息
 * @dateTime 2019-04-25 17:39
 * @className com.meeting.meeting.groupmessage.service.impl.GroupMessageServiceImpl
 */
@Service
public class GroupMessageServiceImpl implements IGroupMessageService {

    @Autowired
    private IGroupMessageDao iGroupMessageDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:02
     * @description: 群聊消息分页
     * @param: [groupMessageVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.groupmessage.GroupMessageVO>
     */
    @Override
    public IPage<GroupMessageVO> getHistoryMessage(GroupMessageVO groupMessageVO, PageVO pageVO) {
        Page<GroupMessageVO> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        return iGroupMessageDao.getHistoryMessage(page, groupMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 19:23
     * @description: 保存群聊记录
     * @param: [groupMessageVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(GroupMessageVO groupMessageVO) {
        return iGroupMessageDao.save(groupMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 12:12
     * @description: 最新30条群聊消息
     * @param: [chatMessageVO]
     * @return: java.util.List<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    @Override
    public List<MessageResultDTO> listHistoryMessage(ChatMessageVO chatMessageVO) {
        return iGroupMessageDao.listMessage(chatMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-22 16:47
     * @description: 群聊删除消息
     * @param: [groupMessageVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int readOrRecallOrDelete(GroupMessageVO groupMessageVO) {
        return iGroupMessageDao.readOrRecallOrDelete(groupMessageVO);
    }
}
