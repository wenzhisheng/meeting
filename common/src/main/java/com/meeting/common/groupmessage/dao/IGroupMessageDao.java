package com.meeting.common.groupmessage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friendmessage.MessageResultDTO;
import com.meeting.common.pojo.groupmessage.GroupMessageVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 群聊消息数据层
 * @dateTime 2019-04-25 17:39
 * @className com.meeting.meeting.groupmessage.dao.IGroupMessageDao
 */
@Repository
public interface IGroupMessageDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:02
     * @description: 群聊消息分页
     * @param: [page, groupMessageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.groupmessage.GroupMessageVO>
     */
    IPage<GroupMessageVO> getHistoryMessage(Page<GroupMessageVO> page, @Param("vo") GroupMessageVO groupMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 19:22
     * @description: 保存群聊记录
     * @param: [groupMessageVO]
     * @return: int
     */
    int save(@Param("vo") GroupMessageVO groupMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 15:01
     * @description: 删除群聊记录
     * @param: [dialogVO]
     * @return: int
     */
    int deleteGroupMessage(@Param("vo") GroupMessageVO groupMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:03
     * @description: 删除群聊记录
     * @param: [dialogVO]
     * @return: int
     */
    int deleteGroupMessage1(@Param("vo") DialogVO dialogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-22 10:50
     * @description: 最新30条消息
     * @param: [chatMessageVO]
     * @return: java.util.List<com.meeting.common.pojo.friendmessage.MessageResultDTO>
     */
    List<MessageResultDTO> listMessage(@Param("vo") ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-22 16:48
     * @description: 群聊删除消息
     * @param: [groupMessageVO]
     * @return: int
     */
    int readOrRecallOrDelete(@Param("vo") GroupMessageVO groupMessageVO);
}
