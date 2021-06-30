package com.meeting.common.friend.service;

import com.meeting.common.pojo.friend.FriendResultDTO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friend.InternalSearchVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.group.GroupVO;

/**
 * @author dameizi
 * @description 好友接口层
 * @dateTime 2019-04-21 14:38
 * @className com.meeting.common.friend.service.IFriendService
 */
public interface IFriendService {

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-17 11:11
	 * @description: 分组列表
	 * @param: [friendVO]
	 * @return: java.util.List<com.meeting.common.pojo.friend.FriendDTO>
	 */
	FriendResultDTO listClass(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-17 11:10
	 * @description: 平铺列表
	 * @param: [friendVO]
	 * @return: java.util.List<com.meeting.common.pojo.friend.FriendVO>
	 */
	FriendResultDTO listFriend(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-04 16:32
	 * @description: 内部搜索
	 * @param: [friendVO]
	 * @return: com.meeting.common.pojo.friend.InternalSearchVO
	 */
	InternalSearchVO internalSearch(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-06 19:21
	 * @description: 是否是好友
	 * @param: [userId, friendId]
	 * @return: boolean
	 */
	boolean isFriend(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-10 19:58
	 * @description: 是否是删除好友
	 * @param: [friendVO]
	 * @return: boolean
	 */
	boolean isDeleteFriend(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-02 15:20
	 * @description: 更新关系好友
	 * @param: [friendVO]
	 * @return: int
	 */
	int update(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-03 19:17
	 * @description: 修改备注
	 * @param: [friendVO]
	 * @return: int
	 */
	int updateAlias(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-08 20:13
	 * @description: 同意添加
	 * @param: [friendVO]
	 * @return: int
	 */
	int save(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-10 14:09
	 * @description: 删除好友
	 * @param: [friendVO]
	 * @return: java.lang.Object
	 */
    Object deleteFriend(FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 14:09
     * @description: 拉黑好友
     * @param: [friendVO]
     * @return: java.lang.Object
     */
	Object blacklistFriend(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-13 19:48
	 * @description: 解除拉黑
	 * @param: [friendVO]
	 * @return: java.lang.Object
	 */
	Object unblock(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-10 21:03
	 * @description: 黑名单好友
	 * @param: [friendVO]
	 * @return: java.lang.Object
	 */
	Object blacklistList(FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-19 11:54
	 * @description: 好友总数会员
	 * @param: [friendApplyVO]
	 * @return: void
	 */
    int countFriendMeember(FriendApplyVO friendApplyVO);

    /**
	 * @author: dameizi
	 * @dateTime: 2019-07-19 11:54
	 * @description: 好友总数目标
	 * @param: [friendApplyVO]
	 * @return: void
	 */
    int countFriendTarget(FriendApplyVO friendApplyVO);

}
