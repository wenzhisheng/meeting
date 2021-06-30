package com.meeting.common.groupmember.service;

import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;

import java.util.List;

/**
 * @author dameizi
 * @description 群组成员接口层
 * @dateTime 2019-04-21 15:35
 * @className com.meeting.common.groupmember.service.IGroupMemberService
 */
public interface IGroupMemberService {

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-19 10:35
	 * @description: 获取群成员列表
	 * @param: [chatMessageVO]
	 * @return: java.util.List<java.lang.String>
	 */
	List<GroupMemberVO> listCluster(ChatMessageVO chatMessageVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:54
	 * @description: 新增群组成员
	 * @param: [groupMemberVO]
	 * @return: int
	 */
	int save(GroupMemberVO groupMemberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-04 20:44
	 * @description: 获取所有群组
	 * @param: [groupVO]
	 * @return: java.util.List<com.meeting.common.pojo.group.GroupResultVO>
	 */
    List<GroupVO> listGroup(GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-16 13:20
     * @description: 获取群当前人数
     * @param: [groupMemberVO]
     * @return: java.lang.Object
	 * @param groupMemberVO
     */
    int getGroupCount(GroupMemberVO groupMemberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 9:59
     * @description: 查找群成员
     * @param: [groupMemberVO]
     * @return: java.util.List<com.meeting.common.pojo.member.MemberVO>
     */
	List<MemberVO> getGroupMember(GroupMemberVO groupMemberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-17 16:52
	 * @description: 获取移除群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	Object getRemoveGroupMember(GroupMemberVO groupMemberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-15 18:42
	 * @description: 添加群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	Object addGroupMember(GroupMemberVO groupMemberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-16 11:03
	 * @description: 移除群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	Object removeGroupMemeber(GroupMemberVO groupMemberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-15 16:57
	 * @description: 退出群组
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	Object quitGroup(GroupMemberVO groupMemberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-16 16:02
	 * @description: 是否是群组成员
	 * @param: [groupVO]
	 * @return: boolean
	 */
	boolean isGroupMember(GroupVO groupVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-17 16:33
	 * @description: 获取添加群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	Object getAddGroupMember(GroupMemberVO groupMemberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-17 19:34
	 * @description: 解散群组
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	Object disbandGroup(GroupMemberVO groupMemberVO);
}
