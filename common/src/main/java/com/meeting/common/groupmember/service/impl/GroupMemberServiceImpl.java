package com.meeting.common.groupmember.service.impl;

import com.meeting.common.constant.CommonConst;
import com.meeting.common.group.dao.IGroupDao;
import com.meeting.common.groupmember.dao.IGroupMemberDao;
import com.meeting.common.groupmember.service.IGroupMemberService;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dameizi
 * @description 群组用户
 * @dateTime 2019-06-25 17:40
 * @className com.meeting.meeting.groupmember.service.impl.GroupMemberServiceImpl
 */
@Service
public class GroupMemberServiceImpl implements IGroupMemberService {

	@Autowired
	private IGroupDao iGroupDao;
	@Autowired
	private IGroupMemberDao iGroupMemberDao;

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-19 10:36
	 * @description: 获取群成员列表
	 * @param: [chatMessageVO]
	 * @return: java.util.List<java.lang.String>
	 */
	@Override
	public List<GroupMemberVO> listCluster(ChatMessageVO chatMessageVO) {
		return iGroupMemberDao.listCluster(chatMessageVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:54
	 * @description: 新增群组成员
	 * @param: [groupMemberVO]
	 * @return: int
	 */
	@Override
	public int save(GroupMemberVO groupMemberVO) {
		return iGroupMemberDao.save(groupMemberVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-04 20:47
	 * @description: 获取所有群组
	 * @param: [groupVO]
	 * @return: java.util.List<com.meeting.common.pojo.group.GroupResultVO>
	 */
	@Override
	public List<GroupVO> listGroup(GroupVO groupVO) {
		List<GroupVO> groupResultVOS = iGroupMemberDao.listGroup(groupVO);
		if (groupResultVOS != null && groupResultVOS.size() != 0){
			return groupResultVOS;
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-16 13:23
	 * @description: 获取群当前人数
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	@Override
	public int getGroupCount(GroupMemberVO groupMemberVO) {
		return iGroupMemberDao.getGroupCount(groupMemberVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-15 10:00
	 * @description: 查找群成员
	 * @param: [groupMemberVO]
	 * @return: java.util.List<com.meeting.common.pojo.member.MemberVO>
	 */
	@Override
	public List<MemberVO> getGroupMember(GroupMemberVO groupMemberVO) {
		return iGroupMemberDao.getGroupMember(groupMemberVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-17 16:53
	 * @description: 获取移除群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	@Override
	public Object getRemoveGroupMember(GroupMemberVO groupMemberVO) {
		return iGroupMemberDao.getRemoveGroupMember(groupMemberVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-15 18:43
	 * @description: 添加群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object addGroupMember(GroupMemberVO groupMemberVO) {
		Integer[] memberIds = groupMemberVO.getMemberIds();
		// 循环添加成员
		for(int i=0; i<memberIds.length; i++){
			groupMemberVO.setMemberId(memberIds[i]);
			// 已经存在不能重复添加
			if (!iGroupMemberDao.isGroupMember1(groupMemberVO)){
				iGroupMemberDao.save(groupMemberVO);
			}
		}
		return MessageFormat.format(CommonConst.ERROR, CommonConst.INSERT_SUCCESS);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-16 11:07
	 * @description: 移除群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object removeGroupMemeber(GroupMemberVO groupMemberVO) {
		Integer[] memberIds = groupMemberVO.getMemberIds();
		for(int i=0; i<memberIds.length; i++){
			groupMemberVO.setMemberId(memberIds[i]);
			iGroupMemberDao.delete(groupMemberVO);
		}
		return CommonConst.ACTION_SUCCESS;
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-15 17:01
	 * @description: 退出群组
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object quitGroup(GroupMemberVO groupMemberVO) {
		return iGroupMemberDao.quitGroup(groupMemberVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-16 16:03
	 * @description: 是否是群组成员
	 * @param: [groupVO]
	 * @return: boolean
	 */
	@Override
	public boolean isGroupMember(GroupVO groupVO) {
		return iGroupMemberDao.isGroupMember(groupVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-17 16:35
	 * @description: 获取添加群组成员
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	@Override
	public Object getAddGroupMember(GroupMemberVO groupMemberVO) {
		return iGroupMemberDao.getAddGroupMember(groupMemberVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-17 19:35
	 * @description: 解散群组
	 * @param: [groupMemberVO]
	 * @return: java.lang.Object
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object disbandGroup(GroupMemberVO groupMemberVO) {
		// 解散群组
		GroupVO groupVO = new GroupVO();
		groupVO.setGroupsId(groupMemberVO.getGroupId());
		groupVO.setMemberId(groupMemberVO.getMemberId());
		iGroupDao.delete(groupVO);
		// 解散群组成员
		return iGroupMemberDao.deleteGroupMember(groupMemberVO);
	}

}
