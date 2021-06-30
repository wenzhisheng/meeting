package com.meeting.common.dialog.service.impl;

import com.meeting.common.constant.CommonConst;
import com.meeting.common.dialog.dao.IDialogDao;
import com.meeting.common.dialog.service.IDialogService;
import com.meeting.common.friendmessage.dao.IFriendMessageDao;
import com.meeting.common.groupmember.dao.IGroupMemberDao;
import com.meeting.common.groupmessage.dao.IGroupMessageDao;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dameizi
 * @description 对话列表业务层
 * @dateTime 2019-07-03 21:04
 * @className com.meeting.common.dialog.service.impl.DialogServiceImpl
 */
@Service
public class DialogServiceImpl implements IDialogService {

    @Autowired
    private IDialogDao iDialogDao;
    @Autowired
    private IGroupMemberDao iGroupMemberDao;
    @Autowired
    private IFriendMessageDao iFriendMessageDao;
    @Autowired
    private IGroupMessageDao iGroupMessageDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 10:25
     * @description: 获取对话列表
     * @param: [dialogVO]
     * @return: java.util.List<com.meeting.common.pojo.dialog.DialogVO>
     */
    @Override
    public List<DialogVO> listDialog(DialogVO dialogVO) {
        return iDialogDao.listDialog(dialogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-03 22:35
     * @description: 保存对话列表
     * @param: [chatMessageVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(ChatMessageVO chatMessageVO) {
        // 目标对话
        if (CommonConst.DIALOG_TYPE_SINGLE.equals(chatMessageVO.getMessageType())){
            iDialogDao.saveTargetSingle(chatMessageVO);
        } else if (CommonConst.DIALOG_TYPE_CLUSTER.equals(chatMessageVO.getMessageType())){
            // 群组需要给每个会员创建一个对话
            ArrayList<ChatMessageVO> listChatMessageVO = new ArrayList<>();
            for(Integer memberId: iGroupMemberDao.listMemberId(chatMessageVO)){
                chatMessageVO.setMemberId(memberId);
                listChatMessageVO.add(chatMessageVO);
            }
            iDialogDao.saveTargetCluster(listChatMessageVO);
        }
        // 自身对话
        return iDialogDao.save(chatMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 10:54
     * @description: 读取消息
     * @param: [chatMessageVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateUnread(ChatMessageVO chatMessageVO) {
        return iDialogDao.updateUnread(chatMessageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 14:39
     * @description: 删除对话
     * @param: [dialogVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteDialogue(DialogVO dialogVO) {
        // 并且删除聊天记录
        if (CommonConst.DIALOG_TYPE_SINGLE.equals(dialogVO.getChatType())){
            iFriendMessageDao.deleteFriendMessage1(dialogVO);
        } else if (CommonConst.DIALOG_TYPE_CLUSTER.equals(dialogVO.getChatType())){
            iGroupMessageDao.deleteGroupMessage1(dialogVO);
        }
        return iDialogDao.deleteDialogue(dialogVO);
    }

}
