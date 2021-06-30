package com.meeting.common.init;

import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import com.meeting.common.pojo.groupmessage.GroupMessageVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;

/**
 * @author dameizi
 * @description 数据层初始化数据
 * @dateTime 2019-07-01 21:32
 * @className com.meeting.common.init.DaoInit
 */
public class DaoInit {

    /** 保存单聊 */
    public static FriendMessageVO saveInitFriendMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        FriendMessageVO friendMessageVO = new FriendMessageVO();
        friendMessageVO.setSendId(memberVO.getMemberId());
        friendMessageVO.setSendAccount(memberVO.getAccount());
        friendMessageVO.setReceiveId(chatMessageVO.getTargetId());
        friendMessageVO.setReceiveAccount(chatMessageVO.getAccount());
        friendMessageVO.setContent(chatMessageVO.getContent());
        friendMessageVO.setMsgid(chatMessageVO.getMsgid());
        return friendMessageVO;
    }

    /** 保存群聊 */
    public static GroupMessageVO saveInitGroupMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        GroupMessageVO groupMessageVO = new GroupMessageVO();
        groupMessageVO.setSendId(memberVO.getMemberId());
        groupMessageVO.setSendAccount(memberVO.getAccount());
        groupMessageVO.setGroupId(chatMessageVO.getTargetId());
        groupMessageVO.setGroupAccount(chatMessageVO.getAccount());
        groupMessageVO.setContent(chatMessageVO.getContent());
        groupMessageVO.setMsgid(chatMessageVO.getMsgid());
        return groupMessageVO;
    }

}
