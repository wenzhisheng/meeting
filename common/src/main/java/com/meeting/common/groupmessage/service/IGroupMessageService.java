package com.meeting.common.groupmessage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.friendmessage.MessageResultDTO;
import com.meeting.common.pojo.groupmessage.GroupMessageVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;

import java.util.List;

/**
 * @author dameizi
 * @description 群聊消息接口层
 * @dateTime 2019-04-25 17:38
 * @className com.meeting.meeting.groupmessage.service.IGroupMessageService
 */
public interface IGroupMessageService {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:59
     * @description: 群聊消息分页
     * @param: [groupMessageVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.groupmessage.GroupMessageVO>
     */
    IPage<GroupMessageVO> getHistoryMessage(GroupMessageVO groupMessageVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 19:23
     * @description: 保存群聊记录
     * @param: [groupMessageVO]
     * @return: int
     */
    int save(GroupMessageVO groupMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 11:47
     * @description: 最新30条群聊消息
     * @param: [historyMessageVO]
     * @return: java.util.List<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    List<MessageResultDTO> listHistoryMessage(ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-22 16:47
     * @description: 群聊删除消息
     * @param: [groupMessageVO]
     * @return: int
     */
    int readOrRecallOrDelete(GroupMessageVO groupMessageVO);
}
