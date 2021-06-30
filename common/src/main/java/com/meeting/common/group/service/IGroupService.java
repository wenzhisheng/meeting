package com.meeting.common.group.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import com.meeting.common.pojo.member.MemberVO;

import java.util.List;

/**
 * @author dameizi
 * @description 群组接口层
 * @dateTime 2019-07-21 15:19
 * @className com.meeting.common.group.service.IGroupService
 */
public interface IGroupService {

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:32
	 * @description: 群组分页
	 * @param: [groupVO, pageVO]
	 * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.group.GroupVO>
	 */
	IPage<GroupVO> page(GroupVO groupVO, PageVO pageVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-05 13:52
	 * @description: 构建群组
	 * @param: [groupVO]
	 * @return: int
	 */
	Object insert(GroupVO groupVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-02 10:33
	 * @description: 更新群组
	 * @param: [groupVO]
	 * @return: int
	 */
	int update(GroupVO groupVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-15 20:14
	 * @description: 编辑群组
	 * @param: [groupVO]
	 * @return: java.lang.Object
	 */
	Object editGroup(GroupVO groupVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-16 11:26
	 * @description: 是否是群组管理员
	 * @param: [groupVO]
	 * @return: java.lang.Object
	 */
	boolean isGroupAdministrator(GroupVO groupVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-19 12:16
	 * @description: 群组数量
	 * @param: [groupVO]
	 * @return: int
	 */
    int countGroup(GroupVO groupVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-08-03 19:40
	 * @description: 群组列表
	 * @param: [memberVO]
	 * @return: java.util.List<com.meeting.common.pojo.group.GroupVO>
	 */
    List<GroupVO> list(MemberVO memberVO);

}
