package com.meeting.common.friendmessage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import com.meeting.common.pojo.friendmessage.MessageResultDTO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.pojo.socketio.HistoryMessage2VO;
import com.meeting.common.pojo.socketio.HistoryMessageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 单聊历史消息数据层
 * @dateTime 2019-04-25 17:40
 * @className com.meeting.meeting.friendmessage.dao.IFriendMessageDao
 */
@Repository
public interface IFriendMessageDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-19 21:32
     * @description: 单聊历史消息分页
     * @param: [page, chatMessageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    IPage<MessageResultDTO> getHistoryMessage2(Page<MessageResultDTO> page, @Param("vo") HistoryMessage2VO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:28
     * @description: 单聊历史消息分页
     * @param: [page, chatMessageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    IPage<MessageResultDTO> getHistoryMessage(Page<MessageResultDTO> page, @Param("vo") HistoryMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 11:59
     * @description: 最新30条单聊消息
     * @param: [chatMessageVO]
     * @return: java.util.List<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    List<MessageResultDTO> listMessage(@Param("vo") ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 21:23
     * @description: 撤回或删除
     * @param: [friendMessageVO]
     * @return: int
     */
    int readOrRecallOrDelete(@Param("vo") FriendMessageVO friendMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 21:23
     * @description: 保存聊天记录
     * @param: [friendMessageVO]
     * @return: int
     */
    int save(@Param("vo") FriendMessageVO friendMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 14:53
     * @description: 删除单聊记录
     * @param: [friendMessageVO]
     * @return: int
     */
    int deleteFriendMessage(@Param("vo") FriendMessageVO friendMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:27
     * @description: 删除单聊记录
     * @param: [dialogVO]
     * @return: int
     */
    int deleteFriendMessage1(@Param("vo") DialogVO dialogVO);
}
