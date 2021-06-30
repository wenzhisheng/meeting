package com.meeting.common.member.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.member.MemberDialogDTO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: dameizi
 * @description: 会员数据层
 * @dateTime 2019-03-29 14:17
 * @className com.weilaizhe.common.exception.OtherReturn
 */
@Repository
public interface IMemberDao {

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-22 18:23
	 * @description: 获取登录时间
	 * @param: [chatMessageVO]
	 * @return: java.lang.Object
	 */
	String getMemberLoginTime(@Param("vo") ChatMessageVO chatMessageVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-03 15:31
	 * @description: 验证码验证
	 * @param: [merchantVO]
	 * @return: int
	 */
	int smsVerify(@Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-03 13:29
	 * @description: 根据会员ID获取会员
	 * @param: [merchantVO]
	 * @return: MerchantVO
	 */
	int getMemberByAccount(@Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-02 23:17
	 * @description: 会员删除
	 * @param: [memberVO]
	 * @return: int
	 */
	int delete(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-02 22:36
	 * @description: 更新会员信息
	 * @param: [memberVO]
	 * @return: int
	 */
	int update(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-12 12:11
	 * @description: 更新头像
	 * @param: [memberVO]
	 * @return: int
	 */
	int updateAvatar(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-18 15:49
	 * @description: 更新登录时间
	 * @param: [memberVO]
	 * @return: int
	 */
	int updateLoginTime(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-27 21:21
	 * @description: 会员修改
	 * @param: [merchantVO]
	 * @return: int
	 */
	int updateMerchant(@Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-02 18:56
	 * @description: 更新会员冻结类型
	 * @param: [merchantVO]
	 * @return: int
	 */
	int updateMerchantByFreezeType(@Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-02 17:54
	 * @description: 登录会员
	 * @param: [memberVO]
	 * @return: MemberVO
	 */
	MemberVO login(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 18:07
	 * @description: 申请会员
	 * @param: [merchantVO]
	 * @return: int
	 */
	int insert(@Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-29 16:11
	 * @description: 分页查询会员信息
	 * @param: [merchantVO, pageVO]
	 * @return: PageResult<MerchantVO>
	 */
	IPage<MemberVO> pageMember(Page<MemberVO> page, @Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-18 17:37
	 * @description: 列表查询会员信息
	 * @param: [memberVO]
	 * @return: java.util.List<com.meeting.common.pojo.member.MemberVO>
	 */
	List<MemberVO> listMember(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-18 17:36
	 * @description: 多ID查询获取好友列表
	 * @param: [ids]
	 * @return: java.util.List<com.meeting.common.pojo.member.MemberVO>
	 */
	List<MemberVO> frientList(@Param("ids") Integer[] ids);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 19:18
	 * @description: 账号和编码是否已被使用
	 * @param: [merchantVO]
	 * @return: int
	 */
	int getCodeOrAccountIsUse(@Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 21:14
	 * @description: 获取最大Id
	 * @param: []
	 * @return: int
	 */
	int getMaxId();

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 21:56
	 * @description: 根据会员编码获取会员信息
	 * @param: [merchantVO]
	 * @return: MerchantVO
	 */
	MemberVO getMerchantByCode(@Param("vo") MemberVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-06-28 19:28
	 * @description: 获取好友对话列表
	 * @param: [set]
	 * @return: java.util.TreeSet<com.meeting.common.pojo.member.MemberVO>
	 */
    ArrayList<MemberVO> getDialogueList(@Param("set") Set<Integer> set);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 18:44
     * @description: 查询会员
     * @param: [memberVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
	MemberVO getMemberById(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-13 15:31
	 * @description: 修改密码查询
	 * @param: [memberVO]
	 * @return: com.meeting.common.pojo.member.MemberVO
	 */
	MemberVO getMemberByPassword(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-06 16:34
	 * @description: 搜索账号
	 * @param: [memberVO]
	 * @return: int
	 */
	MemberVO searchByAccount(@Param("vo") FriendVO friendVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-06 22:14
	 * @description:
	 * @param: [memberVO]
	 * @return: com.meeting.common.pojo.member.MemberVO
	 */
	int changePassword(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-08 18:37
	 * @description: 获取新的好友列表
	 * @param: [memberVO]
	 * @return: java.util.List<com.meeting.common.pojo.member.MemberVO>
	 */
	List<MemberVO> getNewFriendList(@Param("vo") MemberVO memberVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-20 19:38
	 * @description: 对话获取好友信息
	 * @param: [dialogVO]
	 * @return: com.meeting.common.pojo.member.MemberDialogDTO
	 */
	MemberDialogDTO dialogueInfo(@Param("vo") DialogVO dialogVO);
}