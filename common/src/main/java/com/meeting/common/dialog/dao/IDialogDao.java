package com.meeting.common.dialog.dao;

import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 对话列表
 * @dateTime 2019-07-03 21:03
 * @className com.meeting.common.dialog.dao.DialogDao
 */
@Repository
public interface IDialogDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 10:26
     * @description: 获取对话列表
     * @param: [dialogVO]
     * @return: java.util.List<com.meeting.common.pojo.dialog.DialogVO>
     */
    List<DialogVO> listDialog(@Param("vo") DialogVO dialogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-03 22:35
     * @description: 保存对话列表
     * @param: [dialogVO]
     * @return: int
     */
    int save(@Param("vo") ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 21:54
     * @description: 保存对话列表单聊
     * @param: [chatMessageVO]
     * @return: int
     */
    int saveTargetSingle(@Param("vo") ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 21:55
     * @description: 保存对话列表群聊
     * @param: [listChatMessageVO]
     * @return: int
     */
    int saveTargetCluster(@Param("list") List<ChatMessageVO> listChatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 10:55
     * @description: 读取消息
     * @param: [chatMessageVO]
     * @return: java.lang.Object
     */
    int updateUnread(@Param("vo") ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 14:41
     * @description: 删除对话
     * @param: [dialogVO]
     * @return: int
     */
    int deleteDialogue(@Param("vo") DialogVO dialogVO);
}
