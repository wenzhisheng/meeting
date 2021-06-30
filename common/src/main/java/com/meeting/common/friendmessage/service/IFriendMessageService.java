package com.meeting.common.friendmessage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import com.meeting.common.pojo.friendmessage.MessageResultDTO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.pojo.socketio.HistoryMessage2VO;
import com.meeting.common.pojo.socketio.HistoryMessageVO;

import java.util.List;

/**
 * @author dameizi
 * @description 单聊历史消息接口层
 * @dateTime 2019-04-25 17:40
 * @className com.meeting.meeting.friendmessage.service.IFriendMessageService
 */
public interface IFriendMessageService {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-20 10:27
     * @description: 单聊历史消息分页
     * @param: [chatMessageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendmessage.FriendMessageVO>
     */
    IPage<MessageResultDTO> getHistoryMessage2(HistoryMessage2VO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:29
     * @description: 单聊历史消息分页
     * @param: [chatMessageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    IPage<MessageResultDTO> getHistoryMessage(HistoryMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 11:57
     * @description: 最新30条消息
     * @param: [chatMessageVO]
     * @return: java.util.List<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    List<MessageResultDTO> listMessage(ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 21:23
     * @description: 撤回或删除
     * @param: [friendMessageVO]
     * @return: int
     */
    int readOrRecallOrDelete(FriendMessageVO friendMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 15:54
     * @description: 保存聊天记录
     * @param: [friendMessageVO]
     * @return: int
     */
    int save(FriendMessageVO friendMessageVO);
}
