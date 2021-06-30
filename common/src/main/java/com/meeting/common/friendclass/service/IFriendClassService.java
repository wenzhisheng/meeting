package com.meeting.common.friendclass.service;

import com.meeting.common.pojo.friendclass.FriendClassVO;

public interface IFriendClassService {

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:15
	 * @description: 删除好友分组
	 * @param: [friendTypeVO]
	 * @return: int
	 */
	int delete(FriendClassVO friendTypeVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:14
	 * @description: 更新好友分组
	 * @param: [friendTypeVO]
	 * @return: int
	 */
	int update(FriendClassVO friendTypeVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:14
	 * @description: 插入好友分组
	 * @param: [friendClassVO]
	 * @return: int
	 */
	int save(FriendClassVO friendClassVO);

}
