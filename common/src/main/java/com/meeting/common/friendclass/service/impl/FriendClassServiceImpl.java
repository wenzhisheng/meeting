package com.meeting.common.friendclass.service.impl;

import com.meeting.common.friendclass.dao.IFriendClassDao;
import com.meeting.common.friendclass.service.IFriendClassService;
import com.meeting.common.pojo.friendclass.FriendClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dameizi
 * @description 好友分组
 * @dateTime 2019-06-25 17:40
 * @className com.meeting.common.friendclass.service.impl.FriendClassServiceImpl
 */
@Service
public class FriendClassServiceImpl implements IFriendClassService {

	@Autowired
	private IFriendClassDao iFriendClassDao;

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:20
	 * @description: 删除好友分组
	 * @param: [friendTypeVO]
	 * @return: int
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(FriendClassVO friendClassVO) {
		return iFriendClassDao.delete(friendClassVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:19
	 * @description: 更新好友分组
	 * @param: [friendClassVO]
	 * @return: int
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(FriendClassVO friendClassVO) {
		return iFriendClassDao.update(friendClassVO);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:18
	 * @description: 插入好友分组
	 * @param: [friendTypeVO]
	 * @return: int
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int save(FriendClassVO friendTypeVO) {
		return iFriendClassDao.insert(friendTypeVO);
	}

}
