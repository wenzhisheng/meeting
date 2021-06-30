package com.meeting.common.friend.service.impl;

import com.meeting.common.constant.CommonConst;
import com.meeting.common.friend.dao.IFriendDao;
import com.meeting.common.friend.service.IFriendService;
import com.meeting.common.friendapply.dao.IFriendApplyDao;
import com.meeting.common.friendclass.dao.IFriendClassDao;
import com.meeting.common.friendmessage.dao.IFriendMessageDao;
import com.meeting.common.groupmember.dao.IGroupMemberDao;
import com.meeting.common.pojo.friend.*;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dameizi
 * @description 好友业务层
 * @dateTime 2019-04-21 14:38
 * @className com.meeting.meeting.friend.dao.IFriendDao
 */
@Service
public class FriendServiceImpl implements IFriendService {

	@Autowired
	private IFriendDao iFriendDao;
	@Autowired
	private IFriendClassDao iFriendClassDao;
	@Autowired
	private IGroupMemberDao iGroupMemberDao;
	@Autowired
	private IFriendApplyDao iFriendApplyDao;
	@Autowired
	private IFriendMessageDao iFriendMessageDao;

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-17 12:32
	 * @description: 好友分组列表
	 * @param: [friendVO]
	 * @return: java.util.List<com.meeting.common.pojo.friend.FriendDTO>
	 */
	@Override
	public FriendResultDTO listClass(FriendVO friendVO) {
		// 列表或者分类给出类型区分
		FriendResultDTO friendResultDTO = new FriendResultDTO();
		friendResultDTO.setType(CommonConst.SEARCH_CLASS);
		List<FriendDTO> listClass = iFriendClassDao.list(friendVO);
		for (int i=0; i<listClass.size(); i++){
			FriendDTO friendDTO = listClass.get(i);
			friendVO.setFriendClassId(friendDTO.getFriendClassId());
			// 获取分组下好友
			List<FriendResultVO> friendResultVOS = iFriendDao.listFriend(friendVO);
			friendDTO.setFriendCount(friendResultVOS.size());
			// 没有数据返回空对象
			if (friendResultVOS != null && friendResultVOS.size() != 0){
				friendDTO.setFriendList(friendResultVOS);
			}else{
				friendDTO.setFriendList(new ArrayList<>());
			}
		}
		friendResultDTO.setFriendClass(listClass);
		return friendResultDTO;
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-17 12:32
	 * @description: 好友平铺列表
	 * @param: [friendVO]
	 * @return: java.util.List<com.meeting.common.pojo.friend.FriendVO>
	 */
	@Override
	public FriendResultDTO listFriend(FriendVO friendVO) {
		// 列表或者分类给出类型区分
		FriendResultDTO friendResultDTO = new FriendResultDTO();
		friendResultDTO.setType(CommonConst.SEARCH_LIST);
		List<FriendResultVO> friendResultVOS = iFriendDao.listFriend(friendVO);
		// 如果空返回一个空对象，方便渲染
		if (friendResultVOS != null && friendResultVOS.size() != 0){
			friendResultDTO.setFriendList(friendResultVOS);
			return friendResultDTO;
		}else{
			friendResultDTO.setFriendList(new ArrayList<>());
			return friendResultDTO;
		}
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-04 16:38
	 * @description: 内部搜索
	 * @param: [friendVO]
	 * @return: com.meeting.common.pojo.friend.InternalSearchVO
	 */
	@Override
	public InternalSearchVO internalSearch(FriendVO friendVO) {
		InternalSearchVO internalSearchVO = new InternalSearchVO();
		internalSearchVO.setFriend(iFriendDao.internalSearch(friendVO));
		internalSearchVO.setGroup(iGroupMemberDao.internalSearch(friendVO));
		return internalSearchVO;
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-06 20:41
	 * @description: 是否是好友
	 * @param: [friendVO]
	 * @return: boolean
	 */
	@Override
	public boolean isFriend(FriendVO friendVO) {
		return iFriendDao.isFriend(friendVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-10 19:59
	 * @description: 是否是删除好友
	 * @param: [friendVO]
	 * @return: boolean
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean isDeleteFriend(FriendVO friendVO) {
		return iFriendDao.isDeleteFriend(friendVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-02 15:21
	 * @description: 更新关系好友
	 * @param: [friendVO]
	 * @return: int
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(FriendVO friendVO) {
		return iFriendDao.update(friendVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-03 19:17
	 * @description: 修改备注
	 * @param: [friendVO]
	 * @return: int
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateAlias(FriendVO friendVO) {
		return iFriendDao.updateAlias(friendVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-08 20:13
	 * @description: 同意添加
	 * @param: [friendVO]
	 * @return: int
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int save(FriendVO friendVO) {
		return iFriendDao.save(friendVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-10 14:09
	 * @description: 删除好友
	 * @param: [friendVO]
	 * @return: java.lang.Object
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object deleteFriend(FriendVO friendVO) {
		// 删除自己与好友，好友与自己
		iFriendDao.deleteFriend(friendVO);
		// 删除好友申请，只有这个真实删除
		FriendApplyVO friendApplyVO = new FriendApplyVO();
		friendApplyVO.setTargetId(friendVO.getTargetId());
		friendApplyVO.setMemberId(friendVO.getMemberId());
		iFriendApplyDao.deleteFriendApply(friendApplyVO);
		// 删除聊天记录
		FriendMessageVO friendMessageVO = new FriendMessageVO();
		friendMessageVO.setSendId(friendVO.getMemberId());
		friendMessageVO.setReceiveId(friendVO.getTargetId());
		return iFriendMessageDao.deleteFriendMessage(friendMessageVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-10 14:09
	 * @description: 拉黑好友
	 * @param: [friendVO]
	 * @return: java.lang.Object
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object blacklistFriend(FriendVO friendVO) {
		// 拉黑自己与好友，好友与自己
		return iFriendDao.blacklistFriend(friendVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-13 19:49
	 * @description: 解除拉黑
	 * @param: [friendVO]
	 * @return: java.lang.Object
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object unblock(FriendVO friendVO) {
		return iFriendDao.unblock(friendVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-10 21:03
	 * @description: 黑名单好友
	 * @param: [friendVO]
	 * @return: java.lang.Object
	 */
	@Override
	public Object blacklistList(FriendVO friendVO) {
		List<FriendResultVO> friendResultVOS = iFriendDao.blacklistList(friendVO);
		// 如果空返回一个空对象，方便渲染
		if (friendResultVOS != null && friendResultVOS.size() != 0){
			return friendResultVOS;
		}else{
			return new ArrayList<>();
		}
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-19 11:56
	 * @description: 好友总数会员
	 * @param: [friendApplyVO]
	 * @return: int
	 */
	@Override
	public int countFriendMeember(FriendApplyVO friendApplyVO) {
		return iFriendDao.countFriendMeember(friendApplyVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-19 11:56
	 * @description: 好友总数目标
	 * @param: [friendApplyVO]
	 * @return: int
	 */
	@Override
	public int countFriendTarget(FriendApplyVO friendApplyVO) {
		return iFriendDao.countFriendTarget(friendApplyVO);
	}

}
