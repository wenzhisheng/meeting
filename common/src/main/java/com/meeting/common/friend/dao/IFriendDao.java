package com.meeting.common.friend.dao;

import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friend.FriendResultVO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 好友数据层
 * @dateTime 2019-04-21 14:38
 * @className com.meeting.meeting.friend.dao.IFriendDao
 */
@Repository
public interface IFriendDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 21:58
     * @description: 好友列表
     * @param: [friendVO]
     * @return: java.util.List<com.meeting.common.pojo.friend.FriendVO>
     */
    List<FriendVO> list(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-02 13:30
     * @description: 好友列表
     * @param: [friendVO]
     * @return: java.util.List<com.meeting.common.pojo.friend.FriendResultVO>
     */
    List<FriendResultVO> listFriend(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 14:23
     * @description: 删除好友
     * @param: [friendVO]
     * @return: int
     */
    int deleteFriend(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 21:04
     * @description: 拉黑好友
     * @param: [friendVO]
     * @return: int
     */
    int blacklistFriend(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-13 19:52
     * @description: 解除拉黑
     * @param: [friendVO]
     * @return: int
     */
    int unblock(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 15:32
     * @description: 黑名单好友
     * @param: [friendVO]
     * @return: int
     */
    List<FriendResultVO> blacklistList(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 14:25
     * @description: 是否是好友
     * @param: [friendVO]
     * @return: boolean
     */
    boolean isFriend(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 20:00
     * @description: 是否是删除好友
     * @param: [friendVO]
     * @return: boolean
     */
    boolean isDeleteFriend(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 21:58
     * @description: 获取好友
     * @param: [friendVO]
     * @return: com.meeting.common.pojo.friend.FriendVO
     */
    FriendVO getFriend(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 14:26
     * @description: 更新关系好友
     * @param: [friendVO]
     * @return: int
     */
    int update(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-03 19:17
     * @description: 修改备注
     * @param: [friendVO]
     * @return: int
     */
    int updateAlias(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-03 22:12
     * @description: 获取好友消息控制
     * @param: [dialogVO]
     * @return: com.meeting.common.pojo.friend.FriendVO
     */
    FriendVO getFriendByDialog(@Param("vo") DialogVO dialogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-04 16:29
     * @description: 好友内部搜索
     * @param: [friendVO]
     * @return: java.util.List<com.meeting.common.pojo.friend.FriendVO>
     */
    List<FriendVO> internalSearch(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 20:14
     * @description: 同意添加
     * @param: [friendVO]
     * @return: int
     */
    int save(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 19:46
     * @description: 更新添加
     * @param: [friendVO]
     * @return: int
     */
    int updateAdd(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-19 11:58
     * @description: 好友总数会员
     * @param: [friendApplyVO]
     * @return: int
     */
    int countFriendMeember(@Param("vo") FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-19 11:58
     * @description: 好友总数目标
     * @param: [friendApplyVO]
     * @return: int
     */
    int countFriendTarget(@Param("vo") FriendApplyVO friendApplyVO);

}
