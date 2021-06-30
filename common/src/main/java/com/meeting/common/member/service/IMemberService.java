package com.meeting.common.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: dameizi
 * @description: 会员接口层
 * @dateTime 2019-05-29 14:17
 * @className com.meeting.common.member.service.IMemberService
 */
public interface IMemberService {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-22 18:23
     * @description: 获取登录时间
     * @param: [chatMessageVO]
     * @return: java.lang.Object
     */
    Object getMemberLoginTime(ChatMessageVO chatMessageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 20:58
     * @description: 搜索会员
     * @param: [memberVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    Object getMember(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 22:06
     * @description: 修改密码
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    Object changePassword(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 21:29
     * @description: 根据ID或账号获取
     * @param: [memberVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    MemberVO getMemberById(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:17
     * @description: 会员删除
     * @param: [merchantVO]
     * @return: int
     */
    int delete(MemberVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 22:05
     * @description: 更新会员信息
     * @param: [memberVO]
     * @return: int
     */
    int update(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 15:48
     * @description: 更新登录时间
     * @param: [memberVO]
     * @return: int
     */
    int updateLoginTime(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-02 17:54
     * @description: 登录会员
     * @param: [memberVO]
     * @return: MemberVO
     */
    MemberVO login(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 16:11
     * @description: 分页查询会员信息
     * @param: [memberVO, pageVO]
     * @return: PageResult<MerchantVO>
     */
    IPage<MemberVO> pageMember(MemberVO memberVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-30 22:00
     * @description: 列表查询会员信息
     * @param: [merchantVO]
     * @return: java.util.List<MerchantVO>
     */
    List<MemberVO> listMember(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-31 18:07
     * @description: 申请会员
     * @param: [memberVO]
     * @return: int
     */
    Object insert(MultipartFile file, MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-28 19:12
     * @description: 获取好友对话列表
     * @param: [memberVO]
     * @return: java.util.TreeSet<com.meeting.common.pojo.member.MemberVO>
     */
    List<MemberVO> getDialogueList(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 15:31
     * @description: 搜索好友
     * @param: [friendVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    MemberVO searchToAdd(FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 13:17
     * @description: 全局账号搜索
     * @param: [friendVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    MemberVO searchByAccount(FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 20:38
     * @description: 添加好友
     * @param: [friendApplyVO]
     * @return: java.lang.Object
     */
    Object addFriend(FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 20:01
     * @description: 同意添加
     * @param: [memberVO, friendVO]
     * @return: java.lang.Object
     */
    Object agreeAdd(MemberVO memberVO, FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 18:26
     * @description: 获取新的好友列表
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    Object getNewFriendList(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-12 12:12
     * @description: 更新头像
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    Object updateAvatar(MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 19:24
     * @description: 对话获取好友信息
     * @param: [dialogVO]
     * @return: java.lang.Object
     */
    Object dialogueInfo(DialogVO dialogVO);
}
