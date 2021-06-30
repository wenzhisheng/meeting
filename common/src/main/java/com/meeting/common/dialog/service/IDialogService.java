package com.meeting.common.dialog.service;

import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;

import java.util.List;

/**
 * @author dameizi
 * @description 对话接口
 * @dateTime 2019-07-03 21:03
 * @className com.meeting.common.dialog.service.IDialogService
 */
public interface IDialogService {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 10:24
     * @description: 获取对话列表
     * @param: [dialogVO]
     * @return: java.util.List<com.meeting.common.pojo.dialog.DialogVO>
     */
    List<DialogVO> listDialog(DialogVO dialogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-04 10:30
     * @description: 保存对话列表
     * @param: [dialogVO]
     * @return: int
     */
    int save(ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 10:53
     * @description: 读取消息
     * @param: [chatMessageVO]
     * @return: java.lang.Object
     */
    Object updateUnread(ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 14:39
     * @description: 删除对话
     * @param: [dialogVO]
     * @return: java.lang.Object
     */
    Object deleteDialogue(DialogVO dialogVO);
}
