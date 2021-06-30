package com.meeting.common.groupmember.dao;

import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 群组成员数据层
 * @dateTime 2019-04-21 15:35
 * @className com.meeting.meeting.groupuser.dao.IGroupMemberDao
 */
@Repository
public interface IGroupMemberDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-19 10:39
     * @description: 获取群成员列表
     * @param: [chatMessageVO]
     * @return: java.util.List<com.meeting.common.pojo.groupmember.GroupMemberVO>
     */
    List<GroupMemberVO> listCluster(@Param("vo") ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 11:26
     * @description: 新增群成员
     * @param: [groupMemberVO]
     * @return: int
     */
    int save(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-04 16:34
     * @description: 群组内部搜索
     * @param: [friendVO]
     * @return: java.util.List<com.meeting.common.pojo.groupmember.GroupMemberVO>
     */
    List<GroupMemberVO> internalSearch(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-04 20:47
     * @description: 获取所有群组
     * @param: [groupVO]
     * @return: java.util.List<com.meeting.common.pojo.group.GroupResultVO>
     */
    List<GroupVO> listGroup(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:00
     * @description: 查找群成员
     * @param: [groupMemberVO]
     * @return: java.util.List<com.meeting.common.pojo.member.MemberVO>
     */
    List<MemberVO> getGroupMember(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-17 16:53
     * @description: 获取移除群组成员
     * @param: [groupMemberVO]
     * @return: java.util.List<com.meeting.common.pojo.member.MemberVO>
     */
    List<MemberVO> getRemoveGroupMember(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 17:02
     * @description: 退出群组
     * @param: [groupMemberVO]
     * @return: int
     */
    int quitGroup(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-16 11:05
     * @description: 移除群组成员
     * @param: [groupMemberVO]
     * @return: void
     */
    int delete(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-17 19:41
     * @description: 解散群组成员
     * @param: [groupMemberVO]
     * @return: int
     */
    int deleteGroupMember(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-16 13:23
     * @description: 获取群当前人数
     * @param: [groupMemberVO]
     * @return: int
     */
    int getGroupCount(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:52
     * @description: 获取群当前人数
     * @param: [chatMessageVO]
     * @return: java.lang.String
     */
    String getGroupCountByDialog(@Param("vo") ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-16 16:03
     * @description: 是否是群组成员
     * @param: [groupVO]
     * @return: boolean
     */
    boolean isGroupMember(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:52
     * @description: 是否是群组成员
     * @param: [groupMemberVO]
     * @return: boolean
     */
    boolean isGroupMember1(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-17 16:36
     * @description: 获取添加群组成员
     * @param: [groupMemberVO]
     * @return: java.lang.Object
     */
    List<MemberVO> getAddGroupMember(@Param("vo") GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 20:19
     * @description: 对话信息群成员
     * @param: [dialogVO]
     * @return: java.util.List<com.meeting.common.pojo.member.MemberDialogDTO>
     */
    List<MemberVO> listMember(@Param("vo") DialogVO dialogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-23 14:40
     * @description: 获取群组成员ID
     * @param: [chatMessageVO]
     * @return: java.util.List<java.lang.Integer>
     */
    List<Integer> listMemberId(@Param("vo") ChatMessageVO chatMessageVO);
}
