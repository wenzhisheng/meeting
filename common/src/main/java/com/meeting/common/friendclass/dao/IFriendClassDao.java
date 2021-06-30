package com.meeting.common.friendclass.dao;

import com.meeting.common.pojo.friend.FriendDTO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.friendclass.FriendClassVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 好友分组
 * @dateTime 2019-04-21 14:58
 * @className com.meeting.meeting.friendtype.dao.IFriendTypeDao
 */
@Repository
public interface IFriendClassDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:17
     * @description: 好友分组列表
     * @param: [friendVO]
     * @return: java.util.List<com.meeting.common.pojo.friend.FriendDTO>
     */
    List<FriendDTO> list(@Param("vo") FriendVO friendVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-24 21:23
     * @description: 获取分组名称列表
     * @param: [userId]
     * @return: java.util.List<java.lang.String>
     */
    List<FriendClassVO> getFriendTypeName(@Param("userId") Integer userId);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:26
     * @description: 修改好友分组
     * @param: [friendClassVO]
     * @return: int
     */
    int update(@Param("vo") FriendClassVO friendClassVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:24
     * @description: 好友分组获取
     * @param: [friendClassVO]
     * @return: com.meeting.common.pojo.friendclass.FriendClassVO
     */
    FriendClassVO getFriendClass(@Param("vo") FriendClassVO friendClassVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:22
     * @description: 删除好友分组
     * @param: [friendClassVO]
     * @return: int
     */
    int delete(@Param("vo") FriendClassVO friendClassVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 10:26
     * @description: 根据会员ID获取分类ID
     * @param: [memberId]
     * @return: com.meeting.common.pojo.friendclass.FriendClassVO
     */
    FriendClassVO getFriendClassByMemberId(@Param("id") int memberId);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 20:04
     * @description: 添加分组
     * @param: [friendClassVO]
     * @return: int
     */
    int insert(@Param("vo") FriendClassVO friendClassVO);

}
