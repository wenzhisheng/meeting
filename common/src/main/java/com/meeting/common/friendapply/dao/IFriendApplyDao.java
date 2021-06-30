package com.meeting.common.friendapply.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.member.MemberVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author dameizi
 * @description 好友申请数据层
 * @dateTime 2019-04-24 21:52
 * @className com.meeting.common.friendapply.dao.IFriendApplyDao
 */
@Repository
public interface IFriendApplyDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 22:03
     * @description: 分页好友申请
     * @param: [page, friendApplyVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendapply.FriendApplyVO>
     */
    IPage<FriendApplyVO> page(Page<FriendApplyVO> page, @Param("vo") FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 11:44
     * @description: 更新申请状态
     * @param: [friendApplyVO]
     * @return: int
     */
    int updateStatus(@Param("vo") FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 18:10
     * @description: 保存申请好友
     * @param: [friendApplyVO]
     * @return: int
     */
    int save(@Param("vo") FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 18:10
     * @description: 是否存在
     * @param: [friendApplyVO]
     * @return: int
     */
    int isFriendApply(@Param("vo") FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 18:12
     * @description: 已存在就更新备注信息和事件
     * @param: [friendApplyVO]
     * @return: int
     */
    int updateSave(@Param("vo") FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 11:28
     * @description: 获取申请状态
     * @param: [memberId, targetId]
     * @return: com.meeting.common.pojo.friendapply.FriendApplyVO
     */
    FriendApplyVO getFriendApplyByAdd(@Param("memberId") Integer memberId, @Param("targetId") Integer targetId);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 12:09
     * @description: 申请中数量
     * @param: [memberVO]
     * @return: int
     */
    int applyingCount(@Param("vo") MemberVO memberVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 14:46
     * @description: 删除好友申请
     * @param: [friendApplyVO]
     * @return: void
     */
    int deleteFriendApply(@Param("vo") FriendApplyVO friendApplyVO);
}
